import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Accordion, Button } from "react-bootstrap";
import {
  listPayrollRaports,
  deleteAllPayrollRaports,
} from "../../service/PayrollRaportService";

const ListPayrollRaportsComponent = () => {
  const { employeeId } = useParams();

  const navigator = useNavigate();

  const [payrollRaports, setPayrollRaports] = useState([]);

  const getPayrollRaports = () => {
    listPayrollRaports(employeeId)
      .then((res) => setPayrollRaports(res.data))
      .catch((err) => console.error(err));
  };
  useEffect(() => {
    getPayrollRaports();
  }, []);

  const removeRaports = (id) => {
    const isConfirmed = window.confirm(
      "Czy na pewno chcesz usunąć WSZYSTKIE raporty tego pracownika?"
    );
    if (isConfirmed) {
      try {
        deleteAllPayrollRaports(id)
          .then((res) => {
            alert(`Pomyślnie usunięto raporty.`);
            window.location.reload();
          })
          .catch((err) => console.error(err));
      } catch (err) {
        alert(`Błąd podczas usuwania raportów.`);
        console.error(err);
      }
    }
  };

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

  const raportsByYear = payrollRaports.reduce((acc, raport) => {
    const year = raport.year;
    if (!acc[year]) {
      acc[year] = [];
    }
    acc[year].push(raport);

    acc[year].sort((a, b) => a.month - b.month);
    return acc;
  }, {});

  return (
    <section className="text-center" style={{ marginTop: "30px" }}>
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
                    <Button
                      style={{ margin: "10px" }}
                      variant="outline-primary"
                      onClick={() =>
                        navigator(
                          `/employee/${employeeId}/working-logs/${raport.year}/${raport.month}/update-payroll-raport`
                        )
                      }
                    >
                      Aktualizuj
                    </Button>
                  </Accordion.Body>
                </Accordion>
              ))}
            </Accordion.Body>
          </Accordion.Item>
        </Accordion>
      ))}
      {payrollRaports.length > 0 && (
        <Button
          variant="outline-danger"
          style={{ marginTop: "15px" }}
          onClick={() => removeRaports(employeeId)}
        >
          Usuń wszystkie raporty
        </Button>
      )}
      {payrollRaports.length === 0 && (
        <p style={{ color: "black" }}>Brak raportów do wyświetlenia.</p>
      )}
    </section>
  );
};

export default ListPayrollRaportsComponent;
