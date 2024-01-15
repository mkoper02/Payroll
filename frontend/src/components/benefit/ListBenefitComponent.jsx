import React, { useEffect, useState } from "react";
import { listBenefits } from "../../service/BenefitService";
import { Card, Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { deleteBenefit } from "../../service/BenefitService";

const ListBenefitComponent = () => {
  console.log(localStorage.getItem("accessToken"));
  const navigator = useNavigate();
  const [benefits, setBenefits] = useState([]);

  useEffect(() => {
    getAllBenefits();
  }, []);

  const getAllBenefits = () => {
    listBenefits()
      .then((res) => {
        // Sortowanie listy benefitów po ID
        const sortedBenefits = res.data.sort((a, b) => a.id - b.id);
        setBenefits(sortedBenefits);
      })
      .catch((err) => console.error(err));
  };

  const handleUpdateClick = (benefit) => {
    navigator("/update-benefit", { state: { benefit } });
  };

  const removeBenefit = (id) => {
    const isConfirmed = window.confirm(
      "Czy na pewno chcesz usunąć ten benefit?"
    );
    if (isConfirmed) {
      deleteBenefit(id)
        .then((res) => getAllBenefits())
        .catch((err) => console.error(err));
    }
  };

  return (
    <section
      className="text-center"
      style={{ color: "black", marginTop: "15px" }}
    >
      <h2>Lista benefitów</h2>
      <Button
        variant="outline-success"
        onClick={() => navigator(`/add-benefit`)}
      >
        Dodaj nowy benefit
      </Button>
      {benefits.map((benefit) => (
        <Card key={benefit.id} style={{ margin: "15px" }}>
          <Card.Body>
            {benefit.name} {": "} {benefit.cost}zł
            <Button
              style={{ margin: "10px" }}
              variant="outline-primary"
              onClick={() => handleUpdateClick(benefit)}
            >
              Aktualizuj
            </Button>
            <Button
              style={{ margin: "10px" }}
              variant="outline-danger"
              onClick={() => removeBenefit(benefit.id)}
            >
              Usuń
            </Button>
          </Card.Body>
        </Card>
      ))}
    </section>
  );
};

export default ListBenefitComponent;
