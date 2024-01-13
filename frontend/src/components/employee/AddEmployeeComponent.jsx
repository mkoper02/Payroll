import React, { useState, useEffect } from "react";
import api from "../../api/axios";
import { Row, Col, Button, Form, Accordion, Card } from "react-bootstrap";
import { listPositions } from "../../service/PositionService";

const AddEmployeeComponent = () => {
  const [employeeData, setEmployeeData] = useState({
    firstName: "",
    lastName: "",
    dateOfBirth: "",
    email: "",
    phoneNumber: "",
    country: "",
    city: "",
    street: "",
    jobPosition: {
      name: "",
    },
  });
  const [positions, setPositions] = useState([]);
  const [salary, setSalary] = useState({
    contractType: "",
    hours: 0,
    hourlyWage: 0,
  });

  const [user, setUser] = useState({});

  useEffect(() => {
    listPositions()
      .then((res) => setPositions(res.data))
      .catch((err) => console.error(err));
  }, []);

  const employeeHandleChange = (e) => {
    const { name, value } = e.target;
    setEmployeeData((prevEmployeeData) => {
      if (name.startsWith("jobPosition")) {
        // Jeśli pole dotyczy jobPosition, zaktualizuj je w odpowiedni sposób
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
        // W przeciwnym razie zaktualizuj normalnie
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

  const roleHandleChange = (e) => {
    const role = e.target.value === "admin" ? ["ADMIN"] : ["USER"];
    setUser((prevUser) => ({
      ...prevUser,
      role,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    let employeeId = null;

    try {
      const employeeResponse = await api.post("/employee/create", employeeData);
      employeeId = employeeResponse.data.id;
      console.log(typeof employeeId);

      const newUsername = user.username;
      const newPassword = user.password;
      const newRole = user.role;

      const salaryPayload = {
        ...salary,
        id: employeeId,
      };

      const userPayload = {
        id: employeeId,
        username: newUsername,
        password: newPassword,
        role: newRole,
      };

      await api.post("/salary/create", salaryPayload);
      await api.post("/auth/register", userPayload);
      const res = await api.get(`/user/${employeeId}`);
      alert(
        `Pracownik utworzony. Login: ${res.data.username}, Hasło: ${res.data.username} `
      );
      window.location.reload();
    } catch (error) {
      if (employeeId) {
        try {
          await api.delete(`/employee/delete/${employeeId}`);
        } catch (deleteError) {
          console.error(`Błąd podczas usuwania pracownika: ${deleteError}`);
        }
      }

      alert(`Błąd podczas tworzenia pracownika`);
      console.error(error);
    }
  };

  return (
    <Card style={{ padding: "15px 15px", margin: "15px" }}>
      <h2 style={{ color: "black" }}>Wprowadź dane nowego pracownika</h2>
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

                <Form.Group as={Col} controlId="formGridCountry">
                  <Form.Label>Kraj</Form.Label>
                  <Form.Control
                    required
                    name="country"
                    value={employeeData.country}
                    onChange={employeeHandleChange}
                    placeholder="Wprowadź kraj"
                  />
                </Form.Group>
              </Row>

              <Row className="mb-3">
                <Form.Group as={Col} controlId="formGridCity">
                  <Form.Label>Miasto</Form.Label>
                  <Form.Control
                    required
                    name="city"
                    value={employeeData.city}
                    onChange={employeeHandleChange}
                    placeholder="Wprowadź miasto"
                  />
                </Form.Group>

                <Form.Group as={Col} controlId="formGridStreet">
                  <Form.Label>Ulica</Form.Label>
                  <Form.Control
                    required
                    name="street"
                    value={employeeData.street}
                    onChange={employeeHandleChange}
                    placeholder="Wprowadź ulicę"
                  />
                </Form.Group>

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
                    min="0"
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
                    min="0"
                    name="hourlyWage"
                    value={salary.hourlyWage}
                    onChange={salaryHandleChange}
                    placeholder="Wprowadź stawkę godzinową"
                  />
                </Form.Group>
              </Row>
            </Accordion.Body>
          </Accordion.Item>

          {/*REJESTRACJA UŻYTKOWNIKA*/}

          <Accordion.Item eventKey="2">
            <Accordion.Header>Poziom dostępu</Accordion.Header>
            <Accordion.Body>
              <Row className="mb-3">
                <Form.Group as={Col}>
                  <Form.Check
                    type="radio"
                    label="Administrator"
                    name="roleRadio"
                    value="admin"
                    onChange={roleHandleChange}
                    id="radioAdmin"
                  />
                  <Form.Check
                    defaultChecked
                    type="radio"
                    label="Użytkownik"
                    name="roleRadio"
                    value="user"
                    onChange={roleHandleChange}
                    id="radioUser"
                  />
                </Form.Group>
              </Row>
            </Accordion.Body>
          </Accordion.Item>
        </Accordion>
        <Button
          style={{ marginTop: "10px" }}
          variant="outline-success"
          type="submit"
        >
          Dodaj
        </Button>
      </Form>
    </Card>
  );
};

export default AddEmployeeComponent;
