// handlers

/**
 * handles when backend yields a 500
 * @param {Object} json JSON response from backend
 * @returns original JSON if status was not 500
 * @throws error if status was 500
 */
const handle500Error = (json) => {
  if (json.status && json.status === 500) {
    throw new Error(json.message)
  }
  return json
}


// API CALLS
/**
 * fetches all boots from the backend
 * @param {(Boot[]) => void} cb function that renders boot list into DOM
 * @returns void
 */
const fetchAllBoots = (cb) => {
  fetch("/api/v1/boots/")
    .then(res => res.json())
    .then(handle500Error)
    .then(json => cb(json))
    .catch(renderError);
}

/**
 * fetches all different boot types from the backend
 * @param {(String[]) => void} cb function that renders boot types into dropdown options
 * @returns void
 */
const fetchBootTypes = (cb) => {
  fetch("/api/v1/boots/types/")
    .then(res => res.json())
    .then(handle500Error)
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

  fetch(`/api/v1/boots/search?${queryString}`)
    .then(res => res.json())
    .then(handle500Error)
    .then(json => {
      console.log(json)
      cb(json)
    })
    .catch(renderError);
}

const deleteBootById = (bootId, cb) => {
  fetch(`/api/v1/boots/${bootId}`, {
    method: "DELETE"
  })
    .then(res => res.json())
    .then(handle500Error)
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
  const boot = {
    material,
    type,
    size,
    quantity
  }
  fetch(`/api/v1/boots/`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(boot)
  })
    .then(res => res.json())
    .then(handle500Error)
    .then(json => {
      alert(`Successfully added boot with id ${json.id}: (${JSON.stringify(json)})`);
      fetchAllBoots(cb);
    })
    .catch(renderError);
}


const changeBootQuantity = (bootId, action, cb) => {
  fetch(`/api/v1/boots/${bootId}/quantity/${action}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json"
    }
  })
    .then(res => res.json())
    .then(handle500Error)
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
  while (bootsTableBody.firstChild) {
    bootsTableBody.removeChild(bootsTableBody.firstChild);
  }
  boots.forEach((boot) => {
    const bootsRow = document.createElement("tr");
    bootsRow.innerHTML = `
      <td>${boot.id}</td>
      <td>${boot.type}</td>
      <td>${boot.material}</td>
      <td>${boot.size}</td>
      <td>${boot.quantity}</td>
      <td>
        <span class="delete-boot-icon" onclick="deleteBootById(${boot.id}, renderBootsListCallback(document.getElementById('bootsTableBody')));">❌</span>
        <span class="increment-boot-icon" onclick="changeBootQuantity(${boot.id}, 'increment', renderBootsListCallback(document.getElementById('bootsTableBody')));">⬆️</span>
        <span class="decrement-boot-icon" onclick="changeBootQuantity(${boot.id}, 'decrement', renderBootsListCallback(document.getElementById('bootsTableBody')));">⬇️</span>
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
  for (const bootTypesSelect of bootTypesSelects) {
    bootTypes.forEach(bootType => {
      const bootTypeOption = document.createElement("option");
      bootTypeOption.setAttribute("value", bootType);
      bootTypeOption.innerHTML = bootType;
      bootTypesSelect.appendChild(bootTypeOption);
    });
  }
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