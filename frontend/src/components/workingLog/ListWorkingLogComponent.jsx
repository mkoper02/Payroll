import React, { useEffect, useState } from "react";
import { Accordion, Card, Button } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";
import { listWorkingLogs } from "../../service/WorkingLogService";

const ListWorkingLogComponent = () => {
  const navigator = useNavigate();
  const { employeeId } = useParams();
  const [workingLogs, setWorkingLogs] = useState([]);

  const getWorkingLogs = () => {
    listWorkingLogs(employeeId)
      .then((res) => setWorkingLogs(res.data))
      .catch((err) => console.error(err));
  };

  useEffect(() => {
    getWorkingLogs();
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

  return (
    <section className="text-center">
      <Button
        style={{ marginBottom: "10px", marginTop: "15px" }}
        variant="outline-success"
        onClick={() => navigator(`/employee/${employeeId}/add-working-log`)}
      >
        Dodaj nowy log
      </Button>
      {Object.keys(logsByYear).map((year) => (
        <Accordion
          key={year}
          style={{ marginRight: "10px", marginLeft: "10px" }}
        >
          <Accordion.Item eventKey={year}>
            <Accordion.Header>{year}</Accordion.Header>
            <Accordion.Body>
              {logsByYear[year].map((log) => (
                <Card key={log.id} className="mb-2">
                  <Card.Body>
                    Miesiąc: {getMonthName(log.month)}, Godziny:{" "}
                    {log.hoursWorked}
                    <Button
                      style={{ margin: "10px" }}
                      variant="outline-warning"
                      onClick={() =>
                        navigator(
                          `/employee/${employeeId}/working-logs/${log.year}/${log.month}/add-payroll-raport`
                        )
                      }
                    >
                      Generuj raport payrollowy
                    </Button>
                    <Button
                      style={{ margin: "10px" }}
                      variant="outline-primary"
                      onClick={() =>
                        navigator(
                          `/employee/${employeeId}/update-working-log/${log.year}/${log.month}`
                        )
                      }
                    >
                      Aktualizuj
                    </Button>
                  </Card.Body>
                </Card>
              ))}
            </Accordion.Body>
          </Accordion.Item>
        </Accordion>
      ))}
      {workingLogs.length === 0 && (
        <p style={{ color: "black" }}>Brak logów do wyświetlenia.</p>
      )}
    </section>
  );
};

export default ListWorkingLogComponent;
