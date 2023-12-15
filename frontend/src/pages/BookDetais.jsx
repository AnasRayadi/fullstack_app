import React from "react"
import { useLoaderData, useParams, useRouteLoaderData } from "react-router"
import api from "../api/axiosInterceptors"
import BookItem from "../components/book/BookItem"
const BookDetails = () => {
    const param = useParams()
    const id = param.bookId
    const data = useRouteLoaderData('book-details');
    // console.log(data);
    const book = data.bookData;

    return (
        <>
            {/* <h1 className="text-white">book details page bookid : {id}</h1> */}
            <BookItem book={book}/>
        </>
    )

}
export default BookDetails

export async function loader({request, params}){
    // const param = useParams()
    const id = params.bookId
    const res = await api.get(`/books/${parseInt(id)}`);
    const bookData = res.data;
    // console.log(bookData);
    return {bookData};
}
