import api from "../api/axios";

export const listBenefits=()=> api.get(`/benefit`);

export const deleteBenefit=(benefitId)=>api.delete(`/benefit/delete/${benefitId}`);