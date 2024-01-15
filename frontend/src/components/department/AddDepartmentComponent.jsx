import React, { useState } from "react";
import api from "../../api/axios";
import { Row, Col, Button, Form, Card } from "react-bootstrap";

const AddDepartmentComponent = () => {
  const [departmentData, setDepartmentData] = useState({
    name: "",
    country: "",
    city: "",
    street: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setDepartmentData((prevDepartmentData) => {
      return {
        ...prevDepartmentData,
        [name]: value,
      };
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(departmentData);

    try {
      await api.post("/department/create", departmentData);
      alert("Departament został utworzony pomyślnie");
      window.location.reload();
    } catch (error) {
      alert(`Błąd podczas tworzenia departamentu`);
      console.error(error);
    }
  };

  return (
    <Card style={{ padding: "15px 15px", margin: "15px 15px" }}>
      <h2 style={{ color: "black" }}>Wprowadź dane nowego departamentu</h2>
      <Form onSubmit={handleSubmit}>
        <Row className="mb-3">
          <Form.Group as={Col} controlId="formGridname">
            <Form.Label>Nazwa departamentu</Form.Label>
            <Form.Control
              name="name"
              value={departmentData.name}
              onChange={handleChange}
              placeholder="Wprowadź nazwę departamentu"
            />
          </Form.Group>

          <Form.Group as={Col} controlId="formGridCountry">
            <Form.Label>Kraj</Form.Label>
            <Form.Control
              name="country"
              value={departmentData.country}
              onChange={handleChange}
              placeholder="Wprowadź kraj tego departamentu"
            />
          </Form.Group>

          <Form.Group as={Col} controlId="formGridCity">
            <Form.Label>Miasto</Form.Label>
            <Form.Control
              name="city"
              value={departmentData.city}
              onChange={handleChange}
              placeholder="Wprowadź miasto tego departamentu"
            />
          </Form.Group>

          <Form.Group as={Col} controlId="formGridStreet">
            <Form.Label>Ulica</Form.Label>
            <Form.Control
              name="street"
              value={departmentData.street}
              onChange={handleChange}
              placeholder="Wprowadź ulicę"
            />
          </Form.Group>
        </Row>

        <Button variant="outline-success" type="submit">
          Dodaj
        </Button>
      </Form>
    </Card>
  );
};

export default AddDepartmentComponent;
