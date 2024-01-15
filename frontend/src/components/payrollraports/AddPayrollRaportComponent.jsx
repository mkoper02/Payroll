import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { listBenefits } from "../../service/BenefitService";
import { Form, Col, Row, Button } from "react-bootstrap";
import api from "../../api/axios";

const AddPayrollRaportComponent = () => {
  const { employeeId, month, year } = useParams();

  const navigator = useNavigate();

  const numberEmployeeId = parseInt(employeeId, 10);
  const numberMonth = parseInt(month, 10);
  const numberYear = parseInt(year, 10);

  const [payrollRaportData, setPayrollRaportData] = useState({
    employeeId: numberEmployeeId,
    year: numberYear,
    month: numberMonth,
    benefits: [],
  });

  const [bonus, setBonus] = useState("");

  const [benefits, setBenefits] = useState([]);

  useEffect(() => {
    listBenefits()
      .then((res) => {
        const sortedBenefits = res.data.sort((a, b) => a.id - b.id);
        setBenefits(sortedBenefits);
      })
      .catch((err) => console.error(err));
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPayrollRaportData((prevData) => {
      return {
        ...prevData,
        [name]: value,
      };
    });
  };

  const handleBenefitChange = (benefitName) => {
    setPayrollRaportData((prevData) => {
      const isBenefitSelected = prevData.benefits.includes(benefitName);
      const updatedBenefits = isBenefitSelected
        ? prevData.benefits.filter((name) => name !== benefitName)
        : [...prevData.benefits, benefitName];

      return {
        ...prevData,
        benefits: updatedBenefits,
      };
    });
  };

  const handleBonusChange = (e) => {
    setBonus(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const postData = {
      ...payrollRaportData,
      bonus: parseFloat(bonus),
      benefits: payrollRaportData.benefits.map((name) => ({ name })),
    };

    try {
      await api.post(`/payrollraport/create/employee`, postData);
      alert(`Pomyślnie wygenerowano raport.`);
      navigator(`/employee/${employeeId}/working-logs`);
    } catch (err) {
      alert(`Błąd podczas generowania raportu.`);
      console.error(err);
    }
  };

  return (
    <section style={{ color: "black", padding: "15px 15px" }}>
      <Form onSubmit={handleSubmit}>
        <Row className="mb-3">
          <Form.Group as={Col} controlId="formGridYear">
            <Form.Label>Rok</Form.Label>
            <Form.Select
              name="year"
              value={payrollRaportData.year}
              onChange={handleChange}
              disabled
            >
              <option>{payrollRaportData.year}</option>
            </Form.Select>
          </Form.Group>

          <Form.Group as={Col} controlId="formGridMonth">
            <Form.Label>Miesiąc</Form.Label>
            <Form.Control
              name="month"
              value={payrollRaportData.month}
              onChange={handleChange}
              disabled
            />
          </Form.Group>
          <Form.Group as={Col} controlId="formGridBonus">
            <Form.Label>Bonus</Form.Label>
            <Form.Control
              value={bonus}
              onChange={handleBonusChange}
              placeholder="Wpisz bonus (opcjonalnie)"
            />
          </Form.Group>
        </Row>
        <Row className="mb-3">
          <Form.Label style={{ marginTop: "20px" }}>
            Wybierz benefity
          </Form.Label>
          {benefits.map((benefit) => (
            <Form.Group key={benefit.id} controlId={`benefit-${benefit.id}`}>
              <Form.Check
                type="checkbox"
                label={benefit.name}
                onChange={() => handleBenefitChange(benefit.name)}
                checked={payrollRaportData.benefits.includes(benefit.name)}
              />
            </Form.Group>
          ))}
        </Row>

        <Button variant="outline-warning" type="submit">
          Generuj raport
        </Button>
      </Form>
    </section>
  );
};

export default AddPayrollRaportComponent;
