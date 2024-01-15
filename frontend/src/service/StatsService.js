import api from "../api/axios";


export const getStatsForYear = (year)=>api.get(`/stats/${year}`);

export const getStatsForMonth = (year,month)=> api.get(`/stats/${year}/${month}`)