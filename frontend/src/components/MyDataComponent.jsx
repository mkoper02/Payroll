import React, { useEffect, useState } from "react";
import { getEmployeeById } from "../service/EmployeeService";
import { getSalaryById } from "../service/SalaryService";
import { listWorkingLogs } from "../service/WorkingLogService";
import { listPayrollRaports } from "../service/PayrollRaportService";
import { Accordion, Card } from "react-bootstrap";

const MyDataComponent = ({ id }) => {
  const [employeeData, setEmployeeData] = useState(null);
  const [salaryData, setSalaryData] = useState(null);
  const [workingLogs, setWorkingLogs] = useState([]);
  const [payrollRaports, setPayrollRaports] = useState([]);

  const getEmployee = () => {
    getEmployeeById(id)
      .then((res) => setEmployeeData(res.data))
      .catch((err) => console.error(err));
  };

  const getSalary = () => {
    getSalaryById(id)
      .then((res) => setSalaryData(res.data))
      .catch((err) => console.error(err));
  };

  const getWorkingLogs = () => {
    listWorkingLogs(id)
      .then((res) => setWorkingLogs(res.data))
      .catch((err) => console.error(err));
  };

  const getPayrollRaports = () => {
    listPayrollRaports(id)
      .then((res) => setPayrollRaports(res.data))
      .catch((err) => console.error(err));
  };

  useEffect(() => {
    getEmployee();
    getSalary();
    getWorkingLogs();
    getPayrollRaports();
  }, []);

  const logsByYear = workingLogs.reduce((acc, log) => {
    const year = log.year;
    if (!acc[year]) {
      acc[year] = [];
    }
    acc[year].push(log);

    acc[year].sort((a, b) => a.month - b.month);
    return acc;
  }, {});

  const raportsByYear = payrollRaports.reduce((acc, raport) => {
    const year = raport.year;
    if (!acc[year]) {
      acc[year] = [];
    }
    acc[year].push(raport);

    acc[year].sort((a, b) => a.month - b.month);
    return acc;
  }, {});

  const getMonthName = (monthNumber) => {
    const monthNames = [
      "Styczeń",
      "Luty",
      "Marzec",
      "Kwiecień",
      "Maj",
      "Czerwiec",
      "Lipiec",
      "Sierpień",
      "Wrzesień",
      "Październik",
      "Listopad",
      "Grudzień",
    ];
    return monthNames[monthNumber - 1];
  };

  if (!employeeData || !salaryData) {
    return <div style={{ color: "black" }}>Ładowanie danych...</div>;
  }

  return (
    <section
      className="text-center"
      style={{
        color: "black",
        marginTop: "15px",
        marginRight: "10px",
        marginLeft: "10px",
      }}
    >
      <Accordion alwaysOpen>
        <Accordion.Item eventKey="0">
          <Accordion.Header>Dane osobowe</Accordion.Header>
          <Accordion.Body>
            <Card>
              <Card.Body>
                <ul className="list-unstyled">
                  <li key="fullName">
                    {employeeData.firstName} {employeeData.lastName}
                  </li>
                  <li key="dateOfBirth">{employeeData.dateOfBirth}</li>
                  <li key="email">{employeeData.email}</li>
                  <li key="phoneNumber">{employeeData.phoneNumber}</li>
                  <li key="jobPosition.name">
                    {employeeData.jobPosition.name}
                  </li>
                </ul>
              </Card.Body>
            </Card>
          </Accordion.Body>
        </Accordion.Item>
        <Accordion.Item eventKey="1">
          <Accordion.Header>Warunki umowy</Accordion.Header>
          <Accordion.Body>
            <Card>
              <Card.Body>
                <ul className="list-unstyled">
                  <li key="contractType">
                    {salaryData.contractType === "Umowa o prace"
                      ? "Umowa o pracę"
                      : salaryData.contractType}
                  </li>
                  <li key="hours">
                    Liczba godzin w miesiącu: {salaryData.hours}
                  </li>
                  <li key="hourlyWage">
                    Stawka godzinowa: {salaryData.hourlWage} zł
                  </li>
                </ul>
              </Card.Body>
            </Card>
          </Accordion.Body>
        </Accordion.Item>
        <Accordion.Item eventKey="2">
          <Accordion.Header>Working logi</Accordion.Header>
          <Accordion.Body>
            {Object.keys(logsByYear).map((year) => (
              <Accordion key={year} className="mb-2">
                <Accordion.Item eventKey={year}>
                  <Accordion.Header>{year}</Accordion.Header>
                  <Accordion.Body>
                    {logsByYear[year].map((log) => (
                      <Card key={log.id} className="mb-2">
                        <Card.Body>
                          Miesiąc: {getMonthName(log.month)}, Godziny:{" "}
                          {log.hoursWorked}
                        </Card.Body>
                      </Card>
                    ))}
                  </Accordion.Body>
                </Accordion.Item>
              </Accordion>
            ))}
          </Accordion.Body>
        </Accordion.Item>
        <Accordion.Item eventKey="3">
          <Accordion.Header>Raporty payrollowe</Accordion.Header>
          <Accordion.Body>
            {Object.keys(raportsByYear).map((year) => (
              <Accordion
                key={year}
                style={{ marginRight: "10px", marginLeft: "10px" }}
              >
                <Accordion.Item eventKey={year}>
                  <Accordion.Header>{year}</Accordion.Header>
                  <Accordion.Body>
                    {raportsByYear[year].map((raport) => (
                      <Accordion key={raport.id} className="mb-2">
                        <Accordion.Header>
                          {getMonthName(raport.month)}
                        </Accordion.Header>
                        <Accordion.Body>
                          Godziny: {raport.hoursWorked}, Wysokość bonusu:{" "}
                          {parseInt(raport.bonus, 10)}zł{" "}
                          {raport.benefits.length > 0 && (
                            <>
                              , Benefity:
                              <ul className="list-unstyled">
                                {raport.benefits.map((benefit) => (
                                  <li key={benefit.name}>{benefit.name}</li>
                                ))}
                              </ul>
                            </>
                          )}
                          <h3 style={{ fontWeight: "bold" }}>
                            Wysokość wypłaty: {raport.netSalary}zł
                          </h3>
                        </Accordion.Body>
                      </Accordion>
                    ))}
                  </Accordion.Body>
                </Accordion.Item>
              </Accordion>
            ))}
          </Accordion.Body>
        </Accordion.Item>
      </Accordion>
    </section>
  );
};

export default MyDataComponent;
