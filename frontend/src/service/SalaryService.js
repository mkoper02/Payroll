import api from "../api/axios";

export const getSalaryById = (id) => api.get(`/employee/${id}/salary`);

export const getAllSalaries = () => api.get(`/salary`)