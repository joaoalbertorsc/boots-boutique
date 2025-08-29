// handlers

/**
 * Checks if a fetch response is successful. If not, it throws an error.
 * @param {Response} res The response from a fetch call.
 * @returns {Promise<any>} A promise that resolves with the JSON body.
 */
const checkStatusAndGetJSON = (res) => {
    if (!res.ok) {
        // If the response is not OK, we throw an error to be caught by the .catch() block.
        // We try to parse the body for a more specific error message from the server.
        return res.json().then(errorBody => {
            throw new Error(errorBody.message || `Error: ${res.status} ${res.statusText}`);
        }).catch(() => {
            // If the body can't be parsed or is empty, throw a generic error.
            throw new Error(`Error: ${res.status} ${res.statusText}`);
        });
    }
    // If the response is OK, we parse the JSON and continue the promise chain.
    return res.json();
}


// API CALLS
/**
 * fetches all boots from the backend
 * @param {(Boot[]) => void} cb function that renders boot list into DOM
 * @returns void
 */
const fetchAllBoots = (cb) => {
    fetch("/api/v1/boots")
        .then(checkStatusAndGetJSON)
        .then(json => cb(json))
        .catch(renderError);
}

/**
 * fetches all different boot types from the backend
 * @param {(String[]) => void} cb function that renders boot types into dropdown options
 * @returns void
 */
const fetchBootTypes = (cb) => {
    fetch("/api/v1/types")
        .then(checkStatusAndGetJSON)
        .then(json => cb(json))
        .catch(renderError);
}

/**
 * searches boots by different parameters
 * @param {(Boot[]) => void} cb function to render boot search results
 */
const searchBoots = (cb) => {
    const searchBootForm = document.getElementById("searchBoots").elements;
    const material = searchBootForm["material"].value;
    const type = searchBootForm["type"].value;
    const size = parseFloat(searchBootForm["size"].value);
    const quantity = parseInt(searchBootForm["quantity"].value);

    const bootQuery = {
        material,
        type,
        size,
        quantity
    }
    const queryString = Object.keys(bootQuery)
        .filter(k => bootQuery[k])
        .map((key) => `${encodeURIComponent(key)}=${encodeURIComponent(bootQuery[key])}`)
        .join('&');

    fetch(`/api/v1/search?${queryString}`)
        .then(checkStatusAndGetJSON)
        .then(json => {
            console.log(json)
            cb(json)
        })
        .catch(renderError);
}

const deleteBootById = (bootId, cb) => {
    fetch(`/api/v1/${bootId}`, {
        method: "DELETE"
    })
        .then(checkStatusAndGetJSON)
        .then(json => {
            alert(`Deleted Boot ${json.id} from the database (${JSON.stringify(json)}).`);
            fetchAllBoots(cb);
        })
        .catch(renderError);
}

const addNewBoot = (cb) => {
    const addBootForm = document.getElementById("addNewBoot").elements;
    const material = addBootForm["material"].value;
    const type = addBootForm["type"].value;
    const size = parseFloat(addBootForm["size"].value);
    const quantity = parseInt(addBootForm["quantity"].value);

    // Client-side validation
    if (!material || !type || isNaN(size) || isNaN(quantity)) {
        alert("Please fill out all fields correctly.");
        return;
    }

    const boot = {
        material,
        type,
        size,
        quantity,
        bestSeller: false // Add the missing bestSeller field
    }
    fetch(`/api/v1/`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(boot)
    })
        .then(checkStatusAndGetJSON)
        .then(json => {
            alert(`Successfully added boot with id ${json.id}: (${JSON.stringify(json)})`);
            fetchAllBoots(cb);
        })
        .catch(renderError);
}


const changeBootQuantity = (bootId, action, cb) => {
    fetch(`/api/v1/${bootId}/quantity/${action}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(checkStatusAndGetJSON)
        .then(json => {
            alert(`Updated boot: ${JSON.stringify(json)}`)
            fetchAllBoots(cb);
        })
        .catch(renderError);
}

// RENDERING FUNCTIONS

/**
 * alerts user if there was an error making an API request
 * @param {String} message error message to show
 */
const renderError = (message) => {
    alert(`Error calling Boots API: ${message}`);
}

/**
 * renders list of boots into table rows
 * @param {Element} bootsTableBody DOM element to render boots table rows into
 * @param {Boot[]} boots list of boot objects
 * @returns void
 */
const renderBootsListCallback = (bootsTableBody) => (boots) => {
    // Clear the table body before rendering new rows
    bootsTableBody.innerHTML = '';
    boots.forEach((boot) => {
        const bootsRow = document.createElement("tr");
        bootsRow.innerHTML = `
      <td>${boot.id}</td>
      <td>${boot.type}</td>
      <td>${boot.material}</td>
      <td>${boot.size}</td>
      <td>${boot.quantity}</td>
      <td>
        <span class="action-button" onclick="deleteBootById(${boot.id}, renderBootsListCallback(document.getElementById('bootsTableBody')));">❌</span>
        <span class="action-button" onclick="changeBootQuantity(${boot.id}, 'increment', renderBootsListCallback(document.getElementById('bootsTableBody')));">⬆️</span>
        <span class="action-button" onclick="changeBootQuantity(${boot.id}, 'decrement', renderBootsListCallback(document.getElementById('bootsTableBody')));">⬇️</span>
      </td>
    `;
        bootsTableBody.appendChild(bootsRow);
    });
}

/**
 * renders boot types into dropdown options
 * @param {Element[]} bootTypesSelects DOM elements to render options into
 * @param {String[]} bootTypes array of different boot types
 */
const renderBootTypesOptionsCallback = (bootTypesSelects) => (bootTypes) => {
    // Convert the HTMLCollection to an array to safely use forEach
    Array.from(bootTypesSelects).forEach(selectElement => {
        // Preserve the first option if it's a placeholder (like "ANY")
        const firstOption = selectElement.querySelector('option[value=""]');
        
        // Clear existing options
        selectElement.innerHTML = '';

        // Add the placeholder back if it existed
        if (firstOption) {
            selectElement.appendChild(firstOption);
        }

        bootTypes.forEach(bootType => {
            const bootTypeOption = document.createElement("option");
            bootTypeOption.setAttribute("value", bootType);
            bootTypeOption.innerHTML = bootType;
            selectElement.appendChild(bootTypeOption);
        });
    });
}

// calls to initialize and render data on script load
fetchAllBoots(
    renderBootsListCallback(
        document.getElementById("bootsTableBody")
    )
);

fetchBootTypes(
    renderBootTypesOptionsCallback(
        document.getElementsByClassName("boot-types-dropdown")
    )
);