import {useForm} from "react-hook-form";
const Test = () => {

  const rhf = useForm();
  console.log(rhf);

  return (
    <div className="flex justify-center items-center mt-6">
      <div className="text-white">Test Page!</div>
      
    </div>
  );
};
export default Test;
