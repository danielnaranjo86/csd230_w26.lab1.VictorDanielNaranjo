import { useState } from 'react';

function AddBookForm({ onBookAdded }) {
    const [title, setTitle] = useState('');
    const [author, setAuthor] = useState('');
    const [price, setPrice] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();

        // Safety: Ensure price is a number (defaults to 0.0 if empty)
        const safePrice = parseFloat(price) || 0.0;

        const newBook = {
            title: title,
            author: author,
            price: safePrice,
            copies: 10
        };

        fetch('http://localhost:8080/api/rest/books', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newBook)
        })
            .then(async (res) => {
                if (!res.ok) {
                    const text = await res.text();
                    throw new Error(`Server Error ${res.status}: ${text}`);
                }
                return res.json();
            })
            .then(data => {
                onBookAdded(data);
                setTitle(''); setAuthor(''); setPrice('');
            })
            .catch(err => alert("Error adding book: " + err.message));
    };

    return (
        <form onSubmit={handleSubmit} style={{ margin: '20px 0', border: '1px solid #ccc', padding: '10px' }}>
            <h3>Add New Book</h3>
            <div style={{ display: 'flex', gap: '10px' }}>
                <input placeholder="Title" value={title} onChange={e => setTitle(e.target.value)} required />
                <input placeholder="Author" value={author} onChange={e => setAuthor(e.target.value)} required />
                <input type="number" step="0.01" placeholder="Price" value={price} onChange={e => setPrice(e.target.value)} required />
                <button type="submit">Save Book</button>
            </div>
        </form>
    );
}

export default AddBookForm;