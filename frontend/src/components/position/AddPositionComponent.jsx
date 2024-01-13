import { useState, useEffect } from "react";
import { Form, Row, Col, Button } from "react-bootstrap";
import api from "../../api/axios";
import { listDepartments } from "../../service/DepartmentService";

const AddPositionComponent = () => {
  const [positionData, setPositionData] = useState({
    name: "",
    department: {
      name: "",
    },
  });

  const [departments, setDepartments] = useState([]);

  useEffect(() => {
    listDepartments()
      .then((res) => setDepartments(res.data))
      .catch((err) => console.error(err));
  }, []);

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

    try {
      await api.post("/position/create", positionData);
      alert("Stanowisko zostało utworzone pomyślnie");
      window.location.reload();
    } catch (error) {
      alert(`Błąd podczas tworzenia stanowiska`);
      console.error(error);
    }
  };

  return (
    <section style={{ padding: "15px 15px" }}>
      <h2 style={{ color: "black" }}>Dodaj nowe stanowisko</h2>
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

        <Button variant="outline-success" type="submit">
          Dodaj
        </Button>
      </Form>
    </section>
  );
};

export default AddPositionComponent;

/*import { useState, useEffect } from "react";
import { Form, Row, Col, Button, Accordion } from "react-bootstrap";
import api from "../../api/api";

const CreatePosition = () => {
  const [positionData, setPositionData] = useState({
    name: "",
    department: {
      name: "",
    },
  });

  const [departments, setDepartments] = useState();
  useEffect(() => {
    let isMounted = true;
    const controller = new AbortController();
    const getDepartments = async () => {
      try {
        const response = await api.get(`/department`, {
          signal: controller.signal,
        });

        console.log(response.data);
        isMounted && setDepartments(response.data);
      } catch (err) {
        console.error(err);
      }
    };

    getDepartments();

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPositionData((prevPositionData) => {
      if (name.startsWith("department")) {
        // Jeśli pole dotyczy department, zaktualizuj je w odpowiedni sposób
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
        // W przeciwnym razie zaktualizuj normalnie
        return {
          ...prevPositionData,
          [name]: value,
        };
      }
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(positionData);

    try {
      await api.post("/position/create", JSON.stringify(positionData), {
        headers: {
          "Content-Type": "application/json",
        },
      });
      alert("Stanowisko zostało utworzone pomyślnie");
      window.location.reload();

      // Dodaj odpowiednie obsługi sukcesu
    } catch (error) {
      alert(`Błąd podczas tworzenia stanowiska`);
      console.error("Błąd podczas tworzenia stanowiska:", error);
      // Dodaj odpowiednie obsługi błędu
    }
  };

  return (
    <Accordion>
      <Accordion.Item eventKey="0">
        <Accordion.Header>Dodaj nowe stanowisko</Accordion.Header>
        <Accordion.Body>
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
                  {departments.map((dep) => (
                    <option key={dep.id} value={dep.name}>
                      {dep.name}
                    </option>
                  ))}
                </Form.Select>
              </Form.Group>
            </Row>

            <Button variant="outline-primary" type="submit">
              Dodaj
            </Button>
          </Form>
        </Accordion.Body>
      </Accordion.Item>
    </Accordion>
  );
};

export default CreatePosition;
*/
