import React, { useState } from "react";
import { Form, Row, Col, Button, Card } from "react-bootstrap";
import api from "../../api/axios";

const AddBenefitComponent = () => {
  const [benefitData, setBenefitData] = useState({
    name: "",
    cost: 0,
  });

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

    const benefitPayload = {
      ...benefitData,
      cost: parseFloat(benefitData.cost),
    };

    try {
      await api.post("/benefit/create", benefitPayload);
      alert("Benefit został dodany pomyślnie");
      window.location.reload();
    } catch (error) {
      alert(`Błąd podczas tworzenia benefitu.`);
      console.error(error);
    }
  };

  return (
    <Card style={{ padding: "15px 15px", margin: "15px" }}>
      <h2 style={{ color: "black" }}>Wprowadź dane nowego benefitu</h2>
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
              type="number"
              value={benefitData.cost}
              onChange={handleChange}
              placeholder="Wprowadź koszt tego benefitu"
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

export default AddBenefitComponent;
