import React, { useEffect, useState } from "react";
import { listTaxes } from "../../service/TaxService";
import { Card, Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const ListTaxComponent = () => {
  const navigator = useNavigate();
  const [taxes, setTaxes] = useState([]);

  useEffect(() => {
    getAllTaxes();
  }, []);

  const getAllTaxes = () => {
    listTaxes()
      .then((res) => {
        const sortedTaxes = res.data.sort((a, b) => a.id - b.id);
        setTaxes(sortedTaxes);
      })
      .catch((err) => console.error(err));
  };

  const handleUpdateClick = (tax) => {
    navigator("/update-tax", { state: { tax } });
  };

  const removeTax = (id) => {
    const isConfirmed = window.confirm(
      "Czy na pewno chcesz usunąć ten podatek?"
    );
    if (isConfirmed) {
      deleteTax(id)
        .then((res) => getAllTaxes())
        .catch((err) => console.error(err));
    }
  };

  return (
    <section
      className="text-center"
      style={{ color: "black", marginTop: "15px" }}
    >
      <h2>Lista podatków</h2>
      <Button variant="outline-success" onClick={() => navigator(`/add-tax`)}>
        Dodaj nowy podatek
      </Button>
      {taxes.map((tax) => (
        <Card key={tax.id} style={{ margin: "15px" }}>
          <Card.Body>
            {tax.name} {": "} {tax.cost * 100}%
            <Button
              style={{ margin: "10px" }}
              variant="outline-primary"
              onClick={() => handleUpdateClick(tax)}
            >
              Aktualizuj
            </Button>
            <Button
              style={{ margin: "10px" }}
              variant="outline-danger"
              onClick={() => removeTax(tax.id)}
            >
              Usuń
            </Button>
          </Card.Body>
        </Card>
      ))}
    </section>
  );
};

export default ListTaxComponent;
