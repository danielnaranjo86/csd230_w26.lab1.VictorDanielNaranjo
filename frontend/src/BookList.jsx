import { useState, useEffect } from 'react';

function BookList() {
    const [books, setBooks] = useState([]);
    const [loading, setLoading] = useState(true);

    // Fetch books when component mounts
    useEffect(() => {
        fetch('http://localhost:8080/api/rest/books')
            .then(res => res.json())
            .then(data => {
                setBooks(data);
                setLoading(false);
            })
            .catch(err => console.error(err));
    }, []);

    const deleteBook = (id) => {
        if (window.confirm("Delete this book?")) {
            fetch(`http://localhost:8080/api/rest/books/${id}`, { method: 'DELETE' })
                .then(res => {
                    if (res.ok) {
                        // Remove the deleted book from the state
                        setBooks(books.filter(b => b.id !== id));
                    }
                });
        }
    };

    if (loading) return <p>Loading...</p>;

    return (
        <table border="1" cellPadding="10" style={{ width: '100%', textAlign: 'left' }}>
            <thead>
            <tr>
                <th>Title</th><th>Author</th><th>Price</th><th>Actions</th>
            </tr>
            </thead>
            <tbody>
            {books.map(book => (
                <tr key={book.id}>
                    <td>{book.title}</td>
                    <td>{book.author}</td>
                    <td>${book.price}</td>
                    <td>
                        <button onClick={() => deleteBook(book.id)} style={{ color: 'red' }}>Delete</button>
                    </td>
                </tr>
            ))}
            </tbody>
        </table>
    );
}

export default BookList;