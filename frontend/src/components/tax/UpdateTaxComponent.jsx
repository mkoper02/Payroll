import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Form, Row, Col, Button } from "react-bootstrap";
import api from "../../api/axios";

const UpdateTaxComponent = () => {
  const navigator = useNavigate();
  const location = useLocation();
  const [taxData, setTaxData] = useState(
    location.state?.tax || {
      name: "",
      cost: 0,
    }
  );

  const handleChange = (e) => {
    const { name, value } = e.target;
    setTaxData((prevTaxData) => {
      return {
        ...prevTaxData,
        [name]: value,
      };
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const taxId = parseInt(taxData.id);
    try {
      const taxPayload = {
        ...taxData,
        id: taxId,
        cost: parseFloat(taxData.cost),
      };

      await api.put(`/tax/update`, taxPayload);
      alert(`Pomyślnie zaktualizowano podatek.`);
      navigator(`/taxes`);
    } catch (err) {
      alert(`Błąd podczas aktualizowania podatku.`);
      console.error(err);
    }
  };

  return (
    <section style={{ padding: "15px 15px" }}>
      <h2 style={{ color: "black" }}>Aktualizuj dane podatku</h2>
      <Form onSubmit={handleSubmit}>
        <Row className="mb-3">
          <Form.Group as={Col} controlId="formGridName">
            <Form.Label>Nazwa podatku</Form.Label>
            <Form.Control
              name="name"
              value={taxData.name}
              onChange={handleChange}
              placeholder="Wprowadź nazwę podatku"
            />
          </Form.Group>

          <Form.Group as={Col} controlId="formGridCost">
            <Form.Label>Kraj</Form.Label>
            <Form.Control
              name="cost"
              value={taxData.cost}
              onChange={handleChange}
              placeholder="Wprowadź wartość tego podatku w ułamku dziesiętnym (9%=>0.09)"
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

export default UpdateTaxComponent;
