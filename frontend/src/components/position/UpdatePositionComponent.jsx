import React, { useState, useEffect } from "react";
import { Row, Col, Form, Button, Accordion } from "react-bootstrap";
import api from "../../api/axios";
import { useParams } from "react-router-dom";
import { listDepartments } from "../../service/DepartmentService";

const UpdatePositionComponent = () => {
  const { id } = useParams();

  const [positionData, setPositionData] = useState({
    name: "",
    department: {
      name: "",
    },
  });

  const [departments, setDepartments] = useState([]);

  useEffect(() => {
    const positionId = parseInt(id, 10);

    if (id) {
      api
        .get(`/position/${positionId}`)
        .then((res) =>
          setPositionData({
            name: res.data.name,
            department: {
              name: res.data.departmentName,
            },
          })
        )
        .catch((err) => console.error(err));
      listDepartments()
        .then((res) => setDepartments(res.data))
        .catch((err) => console.error(err));
    }
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPositionData((prevPositionData) => {
      if (name.startsWith("department")) {
        const departmentName =
          name === "department.name" ? "name" : name.split(".")[1];
        return {
          ...prevPositionData,
          department: {
            ...prevPositionData.department,
            [departmentName]: value,
          },
        };
      } else {
        return {
          ...prevPositionData,
          [name]: value,
        };
      }
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const positionId = parseInt(id, 10);
    try {
      const positionPayload = {
        ...positionData,
        id: positionId,
      };

      await api.put("/position/update", positionPayload);

      alert("Dane stanowiska zostały zaktualizowane pomyślnie");
      window.location.reload();
    } catch (error) {
      alert("Błąd podczas aktualizacji pracownika");
      console.error(error);
    }
  };

  return (
    <section style={{ padding: "15px 15px" }}>
      <h2 style={{ color: "black", marginLeft: "5px" }}>
        Aktualizuj dane stanowiska
      </h2>
      <Form onSubmit={handleSubmit}>
        <Row className="mb-3">
          <Form.Group as={Col} controlId="formGridPositionName">
            <Form.Label>Nazwa stanowiska</Form.Label>
            <Form.Control
              name="name"
              value={positionData.name}
              onChange={handleChange}
              placeholder="Wprowadź nazwę stanowiska"
            />
          </Form.Group>

          <Form.Group as={Col} controlId="formGridDepartment.name">
            <Form.Label>Departament</Form.Label>
            <Form.Select
              name="department.name"
              value={positionData.department.name}
              onChange={handleChange}
              aria-label="Default select example"
            >
              <option>Wybierz departament...</option>
              {departments &&
                departments.map((department) => (
                  <option key={department.id} value={department.depratmentName}>
                    {department.depratmentName}
                  </option>
                ))}
            </Form.Select>
          </Form.Group>
        </Row>

        <Button variant="outline-primary" type="submit">
          Aktualizuj
        </Button>
      </Form>
    </section>
  );
};

export default UpdatePositionComponent;
