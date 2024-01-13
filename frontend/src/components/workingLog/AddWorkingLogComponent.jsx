import React, { useEffect, useState } from "react";
import { Form, Row, Col, Button, Card } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";
import api from "../../api/axios";

const AddWorkingLogComponent = () => {
  console.log(localStorage.getItem("accessToken"));
  const navigator = useNavigate();
  const { employeeId } = useParams();
  const currentYear = new Date().getFullYear();
  const currentMonth = new Date().getMonth() + 1;
  const numberEmployeeId = parseInt(employeeId, 10);
  const [logData, setLogData] = useState({
    employeeId: numberEmployeeId,
    hoursWorked: 0,
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
    setLogData((prevLogData) => {
      return {
        ...prevLogData,
        [name]: value,
      };
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post("/workinglogs/create/employee", logData);
      alert(`Pomyślnie dodano log`);
      navigator(`/employee/${employeeId}/working-logs`);
    } catch (err) {
      alert(`Błąd podczas dodawania logu.`);
      console.error(err);
    }
  };

  return (
    <Card style={{ color: "black", margin: "15px 15px", padding: "15px 15px" }}>
      <Form onSubmit={handleSubmit}>
        <Row className="mb-3">
          <Form.Group as={Col} controlId="formGridYear">
            <Form.Label>Rok</Form.Label>
            <Form.Select
              name="year"
              value={logData.year}
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
              value={logData.month}
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

          <Form.Group as={Col} controlId="formGridMonth">
            <Form.Label>Przepracowane godziny</Form.Label>
            <Form.Control
              type="number"
              min="0"
              name="hoursWorked"
              value={logData.hoursWorked}
              onChange={handleChange}
              placeholder="Wprowadź liczbę godzin"
            />
          </Form.Group>
        </Row>
        <Button variant="outline-secondary" type="submit">
          Dodaj log
        </Button>
      </Form>
    </Card>
  );
};

export default AddWorkingLogComponent;
