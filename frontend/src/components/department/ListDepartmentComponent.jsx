import React, { useState, useEffect } from "react";
import { Accordion, Button, Card } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import {
  listDepartments,
  deleteDepartment,
} from "../../service/DepartmentService";

const ListDepartmentComponent = () => {
  const [departments, setDepartments] = useState([]);
  const navigator = useNavigate();

  useEffect(() => {
    getAllDepartments();
  }, []);

  const getAllDepartments = () => {
    listDepartments()
      .then((res) => {
        const sortedDepartments = res.data.sort((a, b) => a.id - b.id);
        setDepartments(sortedDepartments);
      })
      .catch((err) => console.error(err));
  };

  const removeDepartment = (id) => {
    const isConfirmed = window.confirm(
      "Czy na pewno chcesz usunąć ten departament?"
    );
    if (isConfirmed) {
      deleteDepartment(id)
        .then((res) => {
          getAllDepartments();
        })
        .catch((err) => console.error(err));
    }
  };

  return (
    <section className="text-center">
      <h2
        className="entity-header"
        style={{ color: "black", marginTop: "15px" }}
      >
        Lista departamentów
      </h2>
      <Button
        style={{ marginBottom: "10px" }}
        variant="outline-success"
        onClick={() => navigator(`/add-department`)}
      >
        Dodaj nowy departament
      </Button>
      {departments.map((department) => (
        <Accordion
          style={{ marginRight: "10px", marginLeft: "10px" }}
          alwaysOpen
          key={department.id}
        >
          <Accordion.Item eventKey="0">
            <Accordion.Header>{department.depratmentName}</Accordion.Header>
            <Accordion.Body>
              <Card>
                <Card.Body>
                  <ul className="list-unstyled">
                    <li key="depratmentName">{department.depratmentName}</li>
                    <li key="country">{department.country}</li>
                    <li key="city">{department.city}</li>
                    <li key="street">{department.street}</li>
                  </ul>
                  <>
                    <span>
                      <Button
                        style={{ marginRight: "10px" }}
                        variant="outline-primary"
                        onClick={() =>
                          navigator(`/update-department/${department.id}`)
                        }
                      >
                        Aktualizuj
                      </Button>
                    </span>
                    <span>
                      <Button
                        variant="outline-danger"
                        onClick={() => removeDepartment(department.id)}
                      >
                        Usuń
                      </Button>
                    </span>
                  </>
                </Card.Body>
              </Card>
            </Accordion.Body>
          </Accordion.Item>
        </Accordion>
      ))}
      <br />
    </section>
  );
};

export default ListDepartmentComponent;
//<CreateEmployee />
/*
<>
                  <span style={{ marginRight: "10px" }}>
                    <Button
                      variant="outline-primary"
                      onClick={() => handleUpdateClick(element.id)}
                    >
                      Aktualizuj
                    </Button>
                  </span>
                  <span>
                    <Button variant="outline-danger">Usuń</Button>
                  </span>
                </>*/
