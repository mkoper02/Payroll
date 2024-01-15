import {resetTokenExpiredAlert} from "../api/axios";

export const logoutUser = () => {  
    resetTokenExpiredAlert();
    localStorage.clear();   
};