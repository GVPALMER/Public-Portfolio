if (annyang) {
    const commands = {
        'hello': () => { alert('Hello world!'); },
        'change the color to *color': (color) => { changeColor(color); },
        'navigate to home': () => { navigateTo('home.html'); },
        'navigate to stocks': () => { navigateTo('stocks.html'); },
        'navigate to dogs': () => { navigateTo('dogs.html'); },
        'load dog breed *breed': (breed) => { fetchBreedInfo(breed); },
    };
    annyang.addCommands(commands);
}

function startListen() {
    annyang.start();
}
function stopListen() {
    annyang.abort();
}
function changeColor(color) {
    document.body.style.backgroundColor = color;
}
function navigateTo(page) {
    window.location.href = page;
}

async function fetchDogImages() {
    const dogImagesContainer = document.getElementById('dogSlider');

    const numberOfImages = 10; 
    const response = await fetch(`https://dog.ceo/api/breeds/image/random/${numberOfImages}`);
    const data = await response.json();

    data.message.forEach(imageUrl => {
        const img = document.createElement('img');
        img.src = imageUrl;
        dogImagesContainer.appendChild(img);
    });

    simpleslider.getSlider();
}

document.addEventListener('DOMContentLoaded', () => {
    fetchDogImages();
});

async function fetchDogImages() {
    const dogImagesContainer = document.getElementById('dogSlider');
    const breedButtonsContainer = document.getElementById('breedButtons');
    const breedInfoContainer = document.getElementById('breed-info-container');

    const numberOfImages = 10; 
    const response = await fetch(`https://dog.ceo/api/breeds/image/random/${numberOfImages}`);
    const data = await response.json();

    data.message.forEach(async (imageUrl, index) => {
        const breed = imageUrl.split('/')[4];  

        const img = document.createElement('img');
        img.src = imageUrl;
        img.alt = breed;
        dogImagesContainer.appendChild(img);

        const button = document.createElement('button');
        button.classList.add('breedButton');
        button.innerText = breed.charAt(0).toUpperCase() + breed.slice(1);
        button.setAttribute('data-breed', breed); 
        breedButtonsContainer.appendChild(button);

        button.addEventListener('click', () => {
            fetchBreedInfo(breed);
        });
    });

    simpleslider.getSlider();
}

async function fetchBreedInfo(breed) {
    const breedInfoContainer = document.getElementById('breed-info-container');

    const response = await fetch(`https://api.thedogapi.com/v1/breeds/search?q=${breed}`);
    const data = await response.json();

    if (data.length === 0) {
        console.error(`No information found for breed: ${breed}`);
        breedInfoContainer.innerHTML = `<p>Information not available for this breed.</p>`;
        return;
    }

    const breedData = data[0];

    const breedName = breedData.name || "Unknown Breed";
    const lifeSpan = breedData.life_span || "Life expectancy unknown.";
    
    let minLife = "Unknown";
    let maxLife = "Unknown";
    if (lifeSpan.includes(" - ")) {
        [minLife, maxLife] = lifeSpan.split(" - ");
        minLife = minLife.replace(" years", "");
        maxLife = maxLife.replace(" years", "");
    }

    const temperament = breedData.temperament

    breedInfoContainer.innerHTML = `
        <div class="breed-info">
            <h3>${breedName}</h3>
            <p><strong>Description:</strong> ${temperament}</p>
            <p><strong>Min Life Expectancy:</strong> ${minLife} years</p>
            <p><strong>Max Life Expectancy:</strong> ${maxLife} years</p>
        </div>
    `;
}










