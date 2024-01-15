import React, { useEffect, useState } from "react";
import { Form, Row, Col, Button, Card } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import api from "../../api/axios";

const AddPayrollRaportAutoComponent = () => {
  const navigator = useNavigate();
  const currentYear = new Date().getFullYear();
  const currentMonth = new Date().getMonth() + 1;
  const [raportData, setRaportData] = useState({
    year: currentYear,
    month: currentMonth,
  });

  const [yearsList, setYearsList] = useState([]);
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

  useEffect(() => {
    setYearsList(
      Array.from({ length: currentYear - 1970 + 1 }, (_, i) => currentYear - i)
    );
  }, [currentYear]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setRaportData((prevRaportData) => {
      return {
        ...prevRaportData,
        [name]: value,
      };
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const isConfirmed = window.confirm(
      `Czy na pewno chcesz automatycznie wygenerować raporty pracownikom?`
    );
    if (isConfirmed) {
      try {
        await api.post("/payrollraport/create/all", raportData);
        alert(`Pomyślnie wygenerowano raporty.`);
        navigator(`/employees`);
      } catch (err) {
        alert(`Błąd podczas generowania raportów.`);
        console.error(err);
      }
    }
  };

  return (
    <Card style={{ color: "black", padding: "15px 15px", margin: "15px 15px" }}>
      <Form onSubmit={handleSubmit}>
        <Row className="mb-3">
          <Form.Group as={Col} controlId="formGridYear">
            <Form.Label>Rok</Form.Label>
            <Form.Select
              name="year"
              value={raportData.year}
              onChange={handleChange}
              aria-label="Default select example"
            >
              <option>Wybierz...</option>
              {yearsList &&
                yearsList.map((year) => (
                  <option key={year} value={year}>
                    {year}
                  </option>
                ))}
            </Form.Select>
          </Form.Group>

          <Form.Group as={Col} controlId="formGridMonth">
            <Form.Label>Miesiąc</Form.Label>
            <Form.Select
              name="month"
              value={raportData.month}
              onChange={handleChange}
              aria-label="Default select example"
            >
              <option>Wybierz...</option>
              {monthNames.map((month, index) => (
                <option key={index} value={index + 1}>
                  {month}
                </option>
              ))}
            </Form.Select>
          </Form.Group>
        </Row>
        <Button variant="outline-secondary" type="submit">
          Generuj raporty
        </Button>
      </Form>
    </Card>
  );
};

export default AddPayrollRaportAutoComponent;
