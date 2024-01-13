import "./App.css";
import { Route, Routes, useNavigate } from "react-router-dom";
import { useEffect } from "react";

import TopNavBar from "./components/TopNavBar.jsx";
//
import ListEmployeeComponent from "./components/employee/ListEmployeeComponent.jsx";
import AddEmployeeComponent from "./components/employee/AddEmployeeComponent.jsx";
import UpdateEmployeeComponent from "./components/employee/UpdateEmployeeComponent.jsx";
//
import ListDepartmentComponent from "./components/department/ListDepartmentComponent.jsx";
import AddDepartmentComponent from "./components/department/AddDepartmentComponent.jsx";
import UpdateDepartmentComponent from "./components/department/UpdateDepartmentComponent.jsx";
//
import ListPositionComponent from "./components/position/ListPositionComponent.jsx";
import AddPositionComponent from "./components/position/AddPositionComponent.jsx";
import UpdatePositionComponent from "./components/position/UpdatePositionComponent.jsx";
//
import ListBenefitComponent from "./components/benefit/ListBenefitComponent.jsx";
import AddBenefitComponent from "./components/benefit/AddBenefitComponent.jsx";
import UpdateBenefitComponent from "./components/benefit/UpdateBenefitComponent.jsx";
//
import ListTaxComponent from "./components/tax/ListTaxComponent.jsx";
import AddTaxComponent from "./components/tax/AddTaxComponent.jsx";
import UpdateTaxComponent from "./components/tax/UpdateTaxComponent.jsx";
//
import ListWorkingLogComponent from "./components/workingLog/ListWorkingLogComponent.jsx";
import AddWorkingLogComponent from "./components/workingLog/AddWorkingLogComponent.jsx";
import UpdateWorkingLogComponent from "./components/workingLog/UpdateWorkingLogComponent.jsx";
import AddWorkingLogAutoComponent from "./components/workingLog/AddWorkingLogAutoComponent.jsx";
//
import ListPayrollRaportsComponent from "./components/payrollraports/ListPayrollRaportsComponent.jsx";
import AddPayrollRaportComponent from "./components/payrollraports/AddPayrollRaportComponent.jsx";
import AddPayrollRaportAutoComponent from "./components/payrollraports/AddPayrollRaportAutoComponent.jsx";
import UpdatePayrollRaportComponent from "./components/payrollraports/UpdatePayrollRaportComponent.jsx";
//
import MyDataComponent from "./components/MyDataComponent.jsx";
//
import ListStatsComponent from "./components/stats/ListStatsComponent.jsx";
//
import PrivateRoute from "./components/PrivateRoute.jsx";

//

function App() {
  const navigate = useNavigate();

  useEffect(() => {
    checkRoleAndRedirect();
  }, []);

  const checkRoleAndRedirect = () => {
    const userRoles = JSON.parse(localStorage.getItem("userRole") || "[]");
    if (!userRoles.includes("ADMIN")) {
      navigate("/my-data");
    }
  };

  return (
    <div className="app">
      <TopNavBar />
      <Routes>
        <Route
          //http://localhost:5173/my-data
          path="/my-data"
          element={<MyDataComponent id={localStorage.getItem("id")} />}
        />
        <Route
          // http://localhost:5173/employee
          path="/employees"
          element={<PrivateRoute component={ListEmployeeComponent} />}
        />
        <Route
          //http://localhost:5173/add-employee
          path="/add-employee"
          element={<PrivateRoute component={AddEmployeeComponent} />}
        />
        <Route
          //http://localhost:5173/edit-employee/:id
          path="/update-employee/:id"
          element={<PrivateRoute component={UpdateEmployeeComponent} />}
        />
        <Route
          // http://localhost:5173/department
          path="/departments"
          element={<PrivateRoute component={ListDepartmentComponent} />}
        />
        <Route
          // http://localhost:5173/department
          path="/add-department"
          element={<PrivateRoute component={AddDepartmentComponent} />}
        />
        <Route
          // http://localhost:5173/update-department/:id
          path="/update-department/:id"
          element={<PrivateRoute component={UpdateDepartmentComponent} />}
        />
        <Route
          // http://localhost:5173/position
          path="/positions"
          element={<PrivateRoute component={ListPositionComponent} />}
        />
        <Route
          // http://localhost:5173/add-position
          path="/add-position"
          element={<PrivateRoute component={AddPositionComponent} />}
        />
        <Route
          // http://localhost:5173/update-position/:id
          path="/update-position/:id"
          element={<PrivateRoute component={UpdatePositionComponent} />}
        />
        <Route
          //http://localhost:5173/benefits
          path="/benefits"
          element={<PrivateRoute component={ListBenefitComponent} />}
        />
        <Route
          // http://localhost:5173/add-benefit
          path="/add-benefit"
          element={<PrivateRoute component={AddBenefitComponent} />}
        />
        <Route
          // http://localhost:5173/update-benefit/:id
          path="/update-benefit"
          element={<PrivateRoute component={UpdateBenefitComponent} />}
        />
        <Route
          // http://localhost:5173/taxes
          path="/taxes"
          element={<PrivateRoute component={ListTaxComponent} />}
        />
        <Route
          //http://localhost:5173/add-tax
          path="/add-tax"
          element={<PrivateRoute component={AddTaxComponent} />}
        />
        <Route
          //http://localhost:5173/update-tax
          path="/update-tax"
          element={<PrivateRoute component={UpdateTaxComponent} />}
        />
        <Route
          // http://localhost:5173/employee/:id/working-logs
          path="/employee/:employeeId/working-logs"
          element={<PrivateRoute component={ListWorkingLogComponent} />}
        />
        <Route
          //http://localhost:5173/employee/:employeeId/add-working-log
          path="/employee/:employeeId/add-working-log"
          element={<PrivateRoute component={AddWorkingLogComponent} />}
        />
        <Route
          //http://localhost:5173/employee/:employeeId/add-working-log
          path="/add-working-logs-auto"
          element={<PrivateRoute component={AddWorkingLogAutoComponent} />}
        />
        <Route
          //http://localhost:5173/employee/:employeeId/update-working-log/:year/:month
          path="/employee/:employeeId/update-working-log/:year/:month"
          element={<PrivateRoute component={UpdateWorkingLogComponent} />}
        />
        <Route
          //http://employee/:id/payroll-raports
          path="/employee/:employeeId/payroll-raports"
          element={<PrivateRoute component={ListPayrollRaportsComponent} />}
        />
        <Route
          //http://localhost:5173/employee/:id/working-logs/:year/:month/add-payroll-raport
          path="/employee/:employeeId/working-logs/:year/:month/add-payroll-raport"
          element={<PrivateRoute component={AddPayrollRaportComponent} />}
        />
        <Route
          //http://localhost:5173/add-payroll-raports-auto
          path="/add-payroll-raports-auto"
          element={<PrivateRoute component={AddPayrollRaportAutoComponent} />}
        />

        <Route
          //http://localhost:5173/employee/:id/working-logs/:year/:month/update-payroll-raport
          path="/employee/:employeeId/working-logs/:year/:month/update-payroll-raport"
          element={<PrivateRoute component={UpdatePayrollRaportComponent} />}
        />

        <Route
          //http://localhost:5173/stats
          path="/stats"
          element={<PrivateRoute component={ListStatsComponent} />}
        />
      </Routes>
    </div>
  );
}

export default App;
