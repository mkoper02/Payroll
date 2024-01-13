import React, { useState } from "react";
import api, { resetTokenExpiredAlert } from "../api/axios";
import { useNavigate } from "react-router-dom";
import { Card, Form, Col, Button } from "react-bootstrap";

const LoginComponent = () => {
  const navigator = useNavigate();
  const [credentials, setCredentials] = useState({
    username: "",
    password: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCredentials((prevCredentials) => {
      return {
        ...prevCredentials,
        [name]: value,
      };
    });
  };

  const handleLogin = async (e) => {
    e.preventDefault();

    localStorage.clear();

    try {
      const loginResponse = await api.post(`/auth/login`, {
        username: credentials.username,
        password: credentials.password,
      });
      resetTokenExpiredAlert();

      const token = loginResponse.data.accessToken;
      const id = loginResponse.data.user.id;

      localStorage.setItem("accessToken", token);
      localStorage.setItem("id", id);
      localStorage.setItem(
        "userRole",
        JSON.stringify(loginResponse.data.user.role)
      );

      alert(`Zalogowano pomyślnie.`);
      navigator(`/my-data`);
    } catch (err) {
      console.error(err);
      alert(`Błąd podczas logowania.`);
    }
  };

  return (
    <section
      className="text-center"
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "100vh",
        backgroundColor: "gray",
      }}
    >
      <Card
        style={{
          maxWidth: "800%",
          alignContent: "center",
          backgroundColor: "black",
          color: "white",
        }}
      >
        <Card.Title>Strona Logowania</Card.Title>
        <Card.Body>
          <Form onSubmit={handleLogin}>
            <Col>
              <Form.Label htmlFor="username">Nazwa użytkownika:</Form.Label>
              <Form.Control
                style={{ backgroundColor: "gray" }}
                type="text"
                name="username"
                id="username"
                value={credentials.username}
                onChange={handleChange}
              />
            </Col>
            <Col>
              <Form.Label htmlFor="password">Hasło:</Form.Label>
              <Form.Control
                style={{ backgroundColor: "gray" }}
                type="password"
                name="password"
                id="password"
                value={credentials.password}
                onChange={handleChange}
              />
            </Col>
            <Button type="submit" style={{ marginTop: "15px" }}>
              Zaloguj się
            </Button>
          </Form>
        </Card.Body>
      </Card>
    </section>
  );
};

export default LoginComponent;
