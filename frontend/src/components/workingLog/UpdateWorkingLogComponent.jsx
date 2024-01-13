import React, { useState } from "react";
import { Form, Row, Col, Button } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";
import api from "../../api/axios";

const UpdateWorkingLogComponent = () => {
  const navigator = useNavigate();
  const { employeeId, year, month } = useParams();
  const numberEmployeeId = parseInt(employeeId, 10);
  const numberYear = parseInt(year, 10);
  const numberMonth = parseInt(month, 10);
  const [logData, setLogData] = useState({
    employeeId: numberEmployeeId,
    hoursWorked: 0,
    year: numberYear,
    month: numberMonth,
  });

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
      await api.put(`/workinglogs/update`, logData);
      alert(`Pomyślnie zaktualizowano log.`);

      navigator(`/employee/${employeeId}/working-logs`);
    } catch (err) {
      alert(`Błąd podczas aktualizowania logu.`);
      console.error(err);
    }
  };

  return (
    <section style={{ color: "black", padding: "15px 15px" }}>
      <Form onSubmit={handleSubmit}>
        <Row className="mb-3">
          <Form.Group as={Col} controlId="formGridYear">
            <Form.Label>Rok</Form.Label>
            <Form.Select
              name="year"
              value={logData.year}
              onChange={handleChange}
              disabled
            >
              <option>{logData.year}</option>
            </Form.Select>
          </Form.Group>

          <Form.Group as={Col} controlId="formGridMonth">
            <Form.Label>Miesiąc</Form.Label>
            <Form.Control
              name="month"
              value={logData.month}
              onChange={handleChange}
              disabled
            />
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
        <Button variant="outline-primary" type="submit">
          Aktualizuj
        </Button>
      </Form>
    </section>
  );
};

export default UpdateWorkingLogComponent;
