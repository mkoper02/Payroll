import React, { useState } from "react";
import { Form, Row, Col, Button } from "react-bootstrap";
import api from "../../api/axios";

const AddTaxComponent = () => {
  const [taxData, setTaxData] = useState({
    name: "",
    cost: 0,
  });

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

    const taxPayload = {
      ...taxData,
      cost: parseFloat(taxData.cost),
    };

    try {
      await api.post("/tax/create", taxPayload);
      alert("Podatek został dodany pomyślnie");
      window.location.reload();
    } catch (error) {
      alert(`Błąd podczas tworzenia podatku`);
      console.error(error);
    }
  };

  return (
    <section style={{ padding: "15px 15px" }}>
      <h2 style={{ color: "black" }}>Wprowadź dane nowego podatku</h2>
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

        <Button variant="outline-success" type="submit">
          Dodaj
        </Button>
      </Form>
    </section>
  );
};

export default AddTaxComponent;
