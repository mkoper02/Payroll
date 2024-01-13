import api from "../api/axios";

export const listEmployees = () => api.get("/employee");

export const getEmployeeById = (id)=>api.get(`/employee/${id}`)

export const deleteEmployee = (employeeId) => api.delete(`/employee/delete/${employeeId}`);
