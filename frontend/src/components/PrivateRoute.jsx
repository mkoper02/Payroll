import React from "react";

const PrivateRoute = ({ component: Component }) => {
  const userRoles = JSON.parse(localStorage.getItem("userRole") || "[]");
  const isAdmin = userRoles.includes("ADMIN");
  return isAdmin ? <Component /> : null;
};

export default PrivateRoute;
