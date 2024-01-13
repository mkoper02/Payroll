import api from "../api/axios";

export const listDepartments = () => api.get("/department");

export const deleteDepartment = (departmentId) => api.delete(`/department/delete/${departmentId}`);

