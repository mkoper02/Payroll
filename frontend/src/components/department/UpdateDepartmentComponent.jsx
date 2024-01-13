import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Form, Row, Col, Button } from "react-bootstrap";
import api from "../../api/axios";

const UpdateDepartmentComponent = () => {
  const { id } = useParams();
  const numberId = parseInt(id, 10);
  const navigator = useNavigate();
  const [departmentData, setDepartmentData] = useState({
    id: numberId,
    depratmentName: "",
    country: "",
    city: "",
    street: "",
  });

  useEffect(() => {
    if (id) {
      api
        .get(`/department/${numberId}`)
        .then((res) => setDepartmentData(res.data))
        .catch((err) => console.error(err));
    }
  }, [id]);

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
      const departmentPayload = {
        ...departmentData,
        name: departmentData.depratmentName,
      };
      await api.put(`/department/update`, departmentPayload);
      alert(`Pomyślnie zaktualizowano dane departamentu.`);
      navigator(`/departments`);
    } catch (err) {
      alert(`Błąd podczas aktualizowania danych departamentu.`);
      console.error(err);
    }
  };

  return (
    <section style={{ padding: "15px 15px" }}>
      <h2 style={{ color: "black" }}>Aktualizuj dane departamentu</h2>
      <Form onSubmit={handleSubmit}>
        <Row className="mb-3">
          <Form.Group as={Col} controlId="formGridName">
            <Form.Label>Nazwa departamentu</Form.Label>
            <Form.Control
              name="name"
              value={departmentData.depratmentName}
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

        <Button variant="outline-primary" type="submit">
          Aktualizuj
        </Button>
      </Form>
    </section>
  );
};

export default UpdateDepartmentComponent;
