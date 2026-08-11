import router from "../router/index.js"
import {ElMessage} from "element-plus";
import http from "@/utils/http.js";

const tools = {

    isLogin() {
        return localStorage.getItem("currentUser") !== null;
    },
    getCurrentUser() {
        return JSON.parse(localStorage.getItem("currentUser"));
    },
    getToken() {
        return localStorage.getItem("token");
    },
}
export default tools
