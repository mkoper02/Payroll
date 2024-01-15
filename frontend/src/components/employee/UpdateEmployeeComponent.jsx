import React, { useState, useEffect } from "react";
import { Row, Col, Form, Button, Accordion, Card } from "react-bootstrap";
import api from "../../api/axios";
import { useParams, useNavigate } from "react-router-dom";
import { listPositions } from "../../service/PositionService";

const UpdateEmployeeComponent = () => {
  const { id } = useParams();
  const navigator = useNavigate();

  const [employeeData, setEmployeeData] = useState({
    firstName: "",
    lastName: "",
    dateOfBirth: "",
    email: "",
    phoneNumber: "",
    jobPosition: {
      name: "",
    },
  });
  const [positions, setPositions] = useState([]);
  const [salary, setSalary] = useState({
    contractType: "",
    hours: 0,
    hourlWage: 0,
  });

  useEffect(() => {
    const employeeId = parseInt(id, 10);
    listPositions()
      .then((res) => setPositions(res.data))
      .catch((err) => console.error(err));

    if (id) {
      api
        .get(`/employee/${employeeId}`)
        .then((res) => setEmployeeData(res.data))
        .catch((err) => console.error(err));

      api
        .get(`/employee/${employeeId}/salary`)
        .then((res) => setSalary(res.data))
        .catch((err) => console.error(err));
    }
  }, [id]);

  const employeeHandleChange = (e) => {
    const { name, value } = e.target;
    setEmployeeData((prevEmployeeData) => {
      if (name.startsWith("jobPosition")) {
        const jobPositionName =
          name === "jobPosition.name" ? "name" : name.split(".")[1];
        return {
          ...prevEmployeeData,
          jobPosition: {
            ...prevEmployeeData.jobPosition,
            [jobPositionName]: value,
          },
        };
      } else {
        return {
          ...prevEmployeeData,
          [name]: value,
        };
      }
    });
  };

  const salaryHandleChange = (e) => {
    const { name, value } = e.target;
    setSalary((prevState) => {
      return {
        ...prevState,
        [name]: value,
      };
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const employeeId = parseInt(id, 10);
    try {
      const employeePayload = {
        ...employeeData,
        id: employeeId, // Przekształcenie id na liczbę, jeśli jest potrzebne
      };

      const salaryPayload = {
        ...salary,
        employeeId: employeeId,
        hourlyWage: parseFloat(salary.hourlWage),
      };

      await api.put("/employee/update", employeePayload);
      await api.put("/salary/update", salaryPayload);

      alert("Dane pracownika zostały zaktualizowane pomyślnie");
      navigator(`/employees`);
    } catch (error) {
      alert("Błąd podczas aktualizacji pracownika");
      console.error(error);
    }
  };

  return (
    <Card style={{ padding: "15px 15px", margin: "15px" }}>
      <h2 style={{ marginTop: "10px", color: "black" }}>
        Aktualizuj dane pracownika
      </h2>
      <Form onSubmit={handleSubmit}>
        {/* DANE OSOBOWE */}

        <Accordion alwaysOpen>
          <Accordion.Item eventKey="0">
            <Accordion.Header>Dane osobowe</Accordion.Header>
            <Accordion.Body>
              <Row className="mb-3">
                <Form.Group as={Col} controlId="formGridFirstName">
                  <Form.Label>Imię</Form.Label>
                  <Form.Control
                    required
                    name="firstName"
                    value={employeeData.firstName}
                    onChange={employeeHandleChange}
                    placeholder="Wprowadź imię"
                  />
                </Form.Group>

                <Form.Group as={Col} controlId="formGridLastName">
                  <Form.Label>Nazwisko</Form.Label>
                  <Form.Control
                    required
                    name="lastName"
                    value={employeeData.lastName}
                    onChange={employeeHandleChange}
                    placeholder="Wprowadź nazwisko"
                  />
                </Form.Group>

                <Form.Group as={Col} controlId="formGridDateOfBirth">
                  <Form.Label>Data urodzenia</Form.Label>
                  <Form.Control
                    required
                    name="dateOfBirth"
                    value={employeeData.dateOfBirth}
                    onChange={employeeHandleChange}
                    placeholder="Wprowadź datę urodzenia (RRRR-MM-DD)"
                  />
                </Form.Group>
              </Row>

              <Row className="mb-3">
                <Form.Group as={Col} controlId="formGridEmail">
                  <Form.Label>Email</Form.Label>
                  <Form.Control
                    required
                    name="email"
                    value={employeeData.email}
                    onChange={employeeHandleChange}
                    placeholder="Wprowadź email"
                  />
                </Form.Group>

                <Form.Group as={Col} controlId="formGridPhoneNumber">
                  <Form.Label>Nr telefonu</Form.Label>
                  <Form.Control
                    required
                    name="phoneNumber"
                    value={employeeData.phoneNumber}
                    onChange={employeeHandleChange}
                    placeholder="Wprowadź nr telefonu (#########)"
                  />
                </Form.Group>
              </Row>

              <Row className="mb-3">
                <Form.Group as={Col} controlId="formGridJobPosition.name">
                  <Form.Label>Stanowisko</Form.Label>
                  <Form.Select
                    name="jobPosition.name"
                    value={employeeData.jobPosition.name}
                    onChange={employeeHandleChange}
                    aria-label="Default select example"
                  >
                    <option>Wybierz stanowisko...</option>
                    {positions &&
                      positions.map((position) => (
                        <option key={position.id} value={position.name}>
                          {position.name}
                        </option>
                      ))}
                  </Form.Select>
                </Form.Group>
              </Row>
            </Accordion.Body>
          </Accordion.Item>

          {/*ZATRUDNIENIE*/}

          <Accordion.Item eventKey="1">
            <Accordion.Header>Warunki umowy</Accordion.Header>
            <Accordion.Body>
              <Row className="mb-3">
                <Form.Group as={Col} controlId="formGridContractType">
                  <Form.Label>Rodzaj umowy</Form.Label>
                  <Form.Select
                    name="contractType"
                    value={salary.contractType}
                    onChange={salaryHandleChange}
                  >
                    <option>Wybierz rodzaj umowy...</option>
                    <option value="Umowa o prace">Umowa o pracę</option>
                    <option value="Umowa-zlecenie">Umowa-zlecenie</option>
                  </Form.Select>
                </Form.Group>

                <Form.Group as={Col} controlId="formGridHours">
                  <Form.Label>Liczba godzin</Form.Label>
                  <Form.Control
                    required
                    type="number"
                    name="hours"
                    value={salary.hours}
                    onChange={salaryHandleChange}
                    placeholder="Wprowadź liczbę godzin"
                  />
                </Form.Group>

                <Form.Group as={Col} controlId="formGridhourlWage">
                  <Form.Label>Stawka godzinowa</Form.Label>
                  <Form.Control
                    required
                    type="number"
                    name="hourlWage"
                    value={salary.hourlWage}
                    onChange={salaryHandleChange}
                    placeholder="Wprowadź stawkę godzinową"
                  />
                </Form.Group>
              </Row>
            </Accordion.Body>
          </Accordion.Item>
        </Accordion>
        <Button
          style={{ marginTop: "10px" }}
          variant="outline-primary"
          type="submit"
        >
          Aktualizuj
        </Button>
      </Form>
    </Card>
  );
};

export default UpdateEmployeeComponent;
