import { useEffect, useState } from "react";
import api from "../api/axiosInterceptors";

const Test = () => {
  const [testText, setTest] = useState();
  useEffect(() => {
    const test = async () => {
      try {
        const response = await api.get("demo-controller");
        console.log(response.data);
        setTest(response.data);
      } catch (err) {
        console.log(err);
      }
    };
    test();
  }, []);

  return (
    <div className="flex justify-center items-center mt-6">
      <div className="text-white">Test Page!</div><br />
      <p>{testText}</p>
    </div>
  );
};
export default Test;
