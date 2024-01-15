import api from "../api/axios";

export const listPayrollRaports=(employeeId)=>api.get(`/employee/${employeeId}/payrollraports`);

export const deleteAllPayrollRaports = (raportId) =>api.delete(`/payrollraport/delete/${raportId}/all`);