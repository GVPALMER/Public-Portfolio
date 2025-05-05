var marker1
var marker2
var marker3

function createMap() {
    var map = L.map('map').setView([38.98, -98], 4);

    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);

    marker1 = L.marker([getRandomInRange(30, 35, 3), getRandomInRange(-90, -100, 3)]).addTo(map);
    marker2 = L.marker([getRandomInRange(30, 35, 3), getRandomInRange(-90, -100, 3)]).addTo(map);
    marker3 = L.marker([getRandomInRange(30, 35, 3), getRandomInRange(-90, -100, 3)]).addTo(map);

    displayMarker()
}
function displayMarker() {
    getLocality(marker1, 'm1', 'm1locality', 'Marker 1');
    getLocality(marker2, 'm2', 'm2locality', 'Marker 2');
    getLocality(marker3, 'm3', 'm3locality', 'Marker 3');
    
}

function getLocality(marker, element, locElement){
    const lat = marker.getLatLng().lat.toFixed(3);
    const lng = marker.getLatLng().lng.toFixed(3);
    document.getElementById(element).innerHTML += ` Latitude ${lat}, Longitude: ${lng}`;

    fetch(`https://api.bigdatacloud.net/data/reverse-geocode-client?latitude=${lat}&longitude=${lng}&localityLanguage=en`)
        .then(response => response.json())
        .then(data => {
            const locality = data.locality || data.city;
            document.getElementById(locElement).innerHTML = `Locality: ${locality}`;
        })
}

window.onload = createMap;

function getRandomInRange(from, to, fixed) {
    return (Math.random() * (to - from) + from).toFixed(fixed) * 1;
    // .toFixed() returns string, so ' * 1' is a trick to convert to number
}