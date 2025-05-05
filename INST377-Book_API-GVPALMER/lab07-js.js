document.getElementById('bookSearch').addEventListener('submit', function(event) {
    event.preventDefault();

    const bookName = document.getElementById('bookName').value.replace(/ /g, '+');
    const loadingMessage = document.getElementById('loadingMessage');
    const resultsTable = document.getElementById('resultsTable');
    const tbody = resultsTable.querySelector('tbody');

    loadingMessage.style.display = 'block';
    resultsTable.style.display = 'none';

    
    fetch(`https://openlibrary.org/search.json?title=${bookName}`)
        .then(response => response.json())
        .then(data => {
            loadingMessage.style.display = 'none';
            tbody.innerHTML = '';

            data.docs.forEach(book => {
                const row = document.createElement('tr');
                const titleCell = document.createElement('td');
                titleCell.textContent = book.title;
                const authorCell = document.createElement('td');
                authorCell.textContent = book.author_name ? book.author_name.join(', ') : 'Unknown';

                row.appendChild(titleCell);
                row.appendChild(authorCell);
                tbody.appendChild(row);
            });
            resultsTable.style.display = 'table';
        })
});
