import {
  Route,
  BrowserRouter as Router,
  RouterProvider,
  Routes,
  createBrowserRouter,
} from "react-router-dom";
import AppLayout from "./layout/app-root";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Books ,{loader as booksLoader} from "./pages/Books";
import BookLayout from "./layout/book-root";
import NewBook from "./pages/NewBook";
import BookDetails, { loader as bookDetailsLoader } from "./pages/BookDetais";
import BookEdit from "./pages/BookEdit";
import Unauthorized from "./pages/Unauthorized";
import RequireAuth from "./components/requireAuth/RequireAuth";
import {action as bookAction} from './components/book/BookForm'
const route = createBrowserRouter([
  {
    path: "/",
    element: <AppLayout />,
    //errorElement: <ErrorPage/>,
    children: [
      // Public Routes
      { index: true, element: <Home /> },
      { path: "login", element: <Login /> },
      { path: "register", element: <Register /> },
      { path: "unauthorized", element: <Unauthorized /> },
      {
        element: <RequireAuth allowedRoles={["ROLE_ADMIN", "ROLE_USER"]} />,
        children: [
          // Protected Routes
          {
            path: "books",
            element: <BookLayout />,
            children: [
              { index: true, element: <Books />, loader: booksLoader },
              {
                path: ":bookId",
                id: "book-details",
                loader: bookDetailsLoader,
                children: [
                  { index: true, element: <BookDetails /> },
                  { path: "edit", element: <BookEdit />, action:bookAction},
                ],
              },
              { path: "new", element: <NewBook />, action:bookAction },
            ],
          },
        ],
      },
    ],
  },
]);
function App() {
  return (
    <RouterProvider router={route} />
    // <Router>
    //   <Routes>
    //     <Route path={"/"} element={<AppLayout />}>
    //       <Route index element={<Home />} />
    //       <Route path="login" element={<Login />} />
    //       <Route path="register" element={<Register />} />
    //       <Route path="test" element={<Test />} />

    //       {/*Protected Routes*/}
    //       <Route path={"books"} element={<BookLayout />}>
    //         <Route index element={<Books/>} />
    //         <Route path={"new"} element={<NewBook />} />
    //       </Route>

    //     </Route>
    //   </Routes>
    // </Router>
  );
}

export default App;
