import { useEffect, useState } from "react";
import BooksTable from "../components/book/BookTable";
// import { useLoaderData} from "react-router";
import api from "../api/axiosInterceptors";
import EditionDateFilter from "../components/BookFilter/EditionDateFilter";
// const DUMMY_BOOKS = [
//   {
//     id: "b1",
//     title: "The Lord of the Rings",
//     image:
//       "https://images-na.ssl-images-amazon.com/images/I/51UoqRAxwEL._SX331_BO1,204,203,200_.jpg",
//     author: "J.R.R. Tolkien",
//     edditionDate: "1954-02-15",
//     description:
//       "The Lord of the Rings is an epic high-fantasy novel written by English author and scholar J. R. R. Tolkien. The story began as a sequel to Tolkien's 1937 fantasy novel The Hobbit, but eventually developed into a much larger work.",
//   },
//   {
//     id: "b2",
//     title: "The Silmarillion",
//     image:
//       "https://images-na.ssl-images-amazon.com/images/I/51UoqRAxwEL._SX331_BO1,204,203,200_.jpg",
//     author: "J.R.R. Tolkien",
//     edditionDate: "1977-09-15",
//     description:
//       "The Silmarillion is a collection of mythopoeic works by English writer J. R. R. Tolkien, edited and published posthumously by his son, Christopher Tolkien, in 1977, with assistance from Guy Gavriel Kay.",
//   },
//   {
//     id: "b3",
//     title: "The Hobbit",
//     image:
//       "https://images-na.ssl-images-amazon.com/images/I/51UoqRAxwEL._SX331_BO1,204,203,200_.jpg",
//     author: "J.R.R. Tolkien",
//     edditionDate: "1937-09-21",
//     description:
//       "The Hobbit, or There and Back Again is a children's fantasy novel by English author J. R. R. Tolkien. It was published on 21 September 1937 to wide critical acclaim, being nominated for the Carnegie Medal and awarded a prize from the New York Herald Tribune for best juvenile fiction.",
//   },
//   {
//     id: "b4",
//     title: "The Fellowship of the Ring",
//     image:
//       "https://images-na.ssl-images-amazon.com/images/I/51UoqRAxwEL._SX331_BO1,204,203,200_.jpg",
//     author: "J.R.R. Tolkien",
//     edditionDate: "1954-07-29",
//     description:
//       "The Fellowship of the Ring is the first of three volumes of the epic novel The Lord of the Rings by the English author J. R. R. Tolkien. It is followed by The Two Towers and The Return of the King.",
//   },
// ];

const Books = () => {
  // const data = useLoaderData()
  // console.log(data.booksData);
  // const books = data.booksData;
  
  // const books = data.booksData;
  const [books, setBooks ] = useState([])
  useEffect(()=>{
    const fetchBooks = async () => {
      const res = await api.get("/books");
      const booksData = await res.data;
      setBooks(booksData)
      console.log(booksData);
    }
    fetchBooks()
  },[])
  
  const filterHandler = (filteredBooks) => {
    setBooks(filteredBooks);
  };

  return (
    <>
      {/* <div className="flex justify-center items-center mt-6">
        <div className="text-white">Books Page!</div>
      </div> */}
      <EditionDateFilter onFilter={filterHandler} />
      <BooksTable books={books} />
    </>
  );
};
export default Books;

// export const loader = async () => {
//   const res = await api.get("/books");
//   const booksData = await res.data;
//   console.log(booksData);
//   return {booksData};
// };
