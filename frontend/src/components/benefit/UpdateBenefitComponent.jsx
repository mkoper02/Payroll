import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Form, Row, Col, Button, Card } from "react-bootstrap";
import api from "../../api/axios";

const UpdateBenefitComponent = () => {
  const navigator = useNavigate();
  const location = useLocation();
  const [benefitData, setBenefitData] = useState(
    location.state?.benefit || {
      name: "",
      cost: 0,
    }
  );

  const handleChange = (e) => {
    const { name, value } = e.target;
    setBenefitData((prevBenefitData) => {
      return {
        ...prevBenefitData,
        [name]: value,
      };
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const benefitId = parseInt(benefitData.id);
    try {
      const benefitPayload = {
        ...benefitData,
        id: benefitId,
        cost: parseFloat(benefitData.cost),
      };

      await api.put(`/benefit/update`, benefitPayload);
      alert(`Pomyślnie zaktualizowano benefit.`);
      navigator(`/benefits`);
    } catch (err) {
      alert(`Błąd podczas aktualizowania benefitu.`);
      console.error(err);
    }
  };

  return (
    <Card style={{ padding: "15px 15px", margin: "15px" }}>
      <h2 style={{ color: "black" }}>Aktualizuj dane benefitu</h2>
      <Form onSubmit={handleSubmit}>
        <Row className="mb-3">
          <Form.Group as={Col} controlId="formGridName">
            <Form.Label>Nazwa benefitu</Form.Label>
            <Form.Control
              name="name"
              value={benefitData.name}
              onChange={handleChange}
              placeholder="Wprowadź nazwę benefitu"
            />
          </Form.Group>

          <Form.Group as={Col} controlId="formGridCost">
            <Form.Label>Koszt</Form.Label>
            <Form.Control
              name="cost"
              value={benefitData.cost}
              onChange={handleChange}
              placeholder="Wprowadź koszt tego benefitu"
            />
          </Form.Group>
        </Row>

        <Button variant="outline-primary" type="submit">
          Aktualizuj
        </Button>
      </Form>
    </Card>
  );
};

export default UpdateBenefitComponent;
