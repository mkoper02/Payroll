import React, { useState, useEffect } from "react";
import { Accordion, Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import {
  listPositions,
  listEmployeesAtPosition,
  deletePosition,
} from "../../service/PositionService";

const ListPositionComponent = () => {
  const [positions, setPositions] = useState([]);
  const [employees, setEmployees] = useState({});

  const navigator = useNavigate();

  useEffect(() => {
    getAllPositions();
  }, []);

  const getAllPositions = () => {
    listPositions()
      .then((res) => setPositions(res.data))
      .catch((err) => console.error(err));
  };

  const removePosition = (id) => {
    const isConfirmed = window.confirm(
      "Czy na pewno chcesz usunąć to stanowisko?"
    );
    if (isConfirmed) {
      deletePosition(id)
        .then((res) => {
          getAllPositions();
        })
        .catch((err) => console.error(err));
    }
  };

  useEffect(() => {
    positions.forEach((position) => {
      listEmployeesAtPosition(position.id)
        .then((res) => {
          setEmployees((prevEmployees) => ({
            ...prevEmployees,
            [position.id]: res.data,
          }));
        })
        .catch((err) => console.error(err));
    });
  }, [positions]);

  return (
    <section className="text-center">
      <h2 style={{ color: "black", marginTop: "15px" }}>Lista stanowisk</h2>
      <Button
        style={{ marginBottom: "10px" }}
        variant="outline-success"
        onClick={() => navigator(`/add-position`)}
      >
        Dodaj nowe stanowisko
      </Button>
      {positions.map((position) => (
        <Accordion
          key={position.id}
          style={{ marginRight: "10px", marginLeft: "10px" }}
          alwaysOpen
        >
          <Accordion.Item eventKey={position.id.toString()}>
            <Accordion.Header>{position.name}</Accordion.Header>
            <Accordion.Body>
              {employees[position.id] ? (
                employees[position.id].map((employee) => (
                  <ul className="list-unstyled" key={employee.id}>
                    <li key={`fullName-${employee.id}`}>
                      {employee.firstName} {employee.lastName}
                    </li>
                  </ul>
                ))
              ) : (
                <p>Ładowanie danych...</p>
              )}

              <>
                <span>
                  <Button
                    variant="outline-primary"
                    style={{ marginRight: "10px" }}
                    onClick={() => navigator(`/update-position/${position.id}`)}
                  >
                    Aktualizuj
                  </Button>
                </span>
                <span>
                  <Button
                    variant="outline-danger"
                    onClick={() => removePosition(position.id)}
                  >
                    Usuń
                  </Button>
                </span>
              </>
            </Accordion.Body>
          </Accordion.Item>
        </Accordion>
      ))}
      <br />
    </section>
  );
};

export default ListPositionComponent;
