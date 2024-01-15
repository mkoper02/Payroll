import React, { useState, useEffect } from "react";
import { Accordion, Button, Card } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import {
  listEmployees,
  deleteEmployee,
} from "../../service/EmployeeService.js";
import { getAllSalaries } from "../../service/SalaryService.js";

const ListEmployeeComponent = () => {
  const [employees, setEmployees] = useState([]);
  const [salaries, setSalaries] = useState({});
  const navigator = useNavigate();

  useEffect(() => {
    getAllEmployees();
  }, []);

  const getAllEmployees = () => {
    listEmployees()
      .then((res) => {
        setEmployees(res.data);
        return getAllSalaries(); // Wywołujemy po pobraniu pracowników
      })
      .then((salariesRes) => {
        // Przekształcamy tablicę wynagrodzeń w obiekt
        const salariesMap = salariesRes.data.reduce((acc, salary) => {
          acc[salary.employeeId] = salary;
          return acc;
        }, {});

        setSalaries(salariesMap);
      })
      .catch((err) => console.error(err));
  };

  const removeEmployee = (id) => {
    const isConfirmed = window.confirm(
      "Czy na pewno chcesz usunąć tego pracownika?"
    );
    if (isConfirmed) {
      deleteEmployee(id)
        .then((res) => {
          getAllEmployees();
        })
        .catch((err) => console.error(err));
    }
  };

  return (
    <section className="text-center">
      <h2 style={{ color: "black", marginTop: "15px" }}>Lista pracowników</h2>
      <>
        <>
          <span>
            <Button
              style={{ marginBottom: "10px", marginRight: "15px" }}
              variant="outline-success"
              onClick={() => navigator(`/add-employee`)}
            >
              Dodaj nowego pracownika
            </Button>
          </span>
          <span>
            <Button
              style={{ marginBottom: "10px", marginRight: "15px" }}
              variant="outline-secondary"
              onClick={() => navigator(`/add-working-logs-auto`)}
            >
              Stwórz logi automatycznie
            </Button>
          </span>
          <span>
            <Button
              style={{ marginBottom: "10px" }}
              variant="outline-warning"
              onClick={() => navigator(`/add-payroll-raports-auto`)}
            >
              Generuj raporty automatycznie
            </Button>
          </span>
        </>

        {employees.map((employee) => (
          <Accordion
            key={employee.id}
            style={{ marginRight: "10px", marginLeft: "10px" }}
            alwaysOpen
          >
            <Accordion.Item eventKey="0">
              <Accordion.Header>
                {employee.firstName} {employee.lastName}
              </Accordion.Header>
              <Accordion.Body>
                <Card>
                  <Card.Body>
                    <ul className="list-unstyled">
                      <li key="fullName">
                        {employee.firstName} {employee.lastName}
                      </li>
                      <li key="dateOfBirth">{employee.dateOfBirth}</li>
                      <li key="email">{employee.email}</li>
                      <li key="phoneNumber">{employee.phoneNumber}</li>
                      <li key="jobPosition.name">
                        {employee.jobPosition.name}
                      </li>
                    </ul>
                    <Accordion>
                      <Accordion.Item>
                        <Accordion.Header>Warunki umowy</Accordion.Header>
                        <Accordion.Body>
                          <Card>
                            <Card.Title></Card.Title>
                            <ul className="list-unstyled">
                              {salaries[employee.id] ? (
                                <>
                                  <li key="contractType">
                                    {salaries[employee.id].contractType}
                                  </li>
                                  <li key="hours">
                                    Liczba godzin w miesiącu:{" "}
                                    {salaries[employee.id].hours}
                                  </li>
                                  <li key="hourlyWage">
                                    Stawka godzinowa:{" "}
                                    {salaries[employee.id].hourlWage} zł
                                  </li>
                                </>
                              ) : (
                                <li>Brak danych o wynagrodzeniu</li>
                              )}
                            </ul>
                          </Card>
                        </Accordion.Body>
                      </Accordion.Item>
                    </Accordion>
                    <>
                      <span>
                        <Button
                          style={{
                            marginTop: "10px",
                            marginBottom: "10px",
                            marginRight: "10px",
                          }}
                          variant="outline-primary"
                          onClick={() =>
                            navigator(`/update-employee/${employee.id}`)
                          }
                        >
                          Aktualizuj
                        </Button>
                      </span>
                      <span>
                        <Button
                          style={{
                            marginTop: "10px",
                            marginBottom: "10px",
                            marginRight: "10px",
                          }}
                          variant="outline-danger"
                          onClick={() => removeEmployee(employee.id)}
                        >
                          Usuń
                        </Button>
                      </span>
                      <span>
                        <Button
                          style={{
                            marginTop: "10px",
                            marginBottom: "10px",
                            marginRight: "10px",
                          }}
                          variant="outline-secondary"
                          onClick={() =>
                            navigator(`/employee/${employee.id}/working-logs`)
                          }
                        >
                          Working logi
                        </Button>
                      </span>
                      <span>
                        <Button
                          variant="outline-warning"
                          onClick={() =>
                            navigator(
                              `/employee/${employee.id}/payroll-raports`
                            )
                          }
                        >
                          Raporty payrollowe
                        </Button>
                      </span>
                    </>
                  </Card.Body>
                </Card>
              </Accordion.Body>
            </Accordion.Item>
          </Accordion>
        ))}
        <br />
      </>
    </section>
  );
};

export default ListEmployeeComponent;
