import axios from "axios";

// axios 객체 생성
export default axios.create({
  baseURL: "https://j7d101.p.ssafy.io/api/seller",
  headers: {
    "Content-type": "application/json",
  },
});


