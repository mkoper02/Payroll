import { Container } from "react-bootstrap";
import { Navbar, Nav } from "react-bootstrap";
import { logoutUser } from "../service/AuthService";
import { useNavigate } from "react-router-dom";

const TopNavBar = () => {
  const navigator = useNavigate();
  const userRoles = JSON.parse(localStorage.getItem("userRole") || "[]");

  // Sprawdzanie, czy użytkownik ma określoną rolę
  const isAdmin = userRoles.includes("ADMIN");

  const logout = () => {
    logoutUser();
    navigator(``);
  };

  return (
    <Navbar bg="dark" variant="dark" expand="lg">
      <Container fluid>
        <Nav className="me-auto">
          <Navbar.Brand href="/my-data" style={{ color: "gold" }}>
            Moje dane
          </Navbar.Brand>

          {isAdmin && (
            <>
              <Navbar.Brand href="/employees" style={{ color: "gold" }}>
                Pracownicy
              </Navbar.Brand>
              <Navbar.Brand href="/departments" style={{ color: "gold" }}>
                Departamenty
              </Navbar.Brand>
              <Navbar.Brand href="/positions" style={{ color: "gold" }}>
                Stanowiska
              </Navbar.Brand>
              <Navbar.Brand href="/benefits" style={{ color: "gold" }}>
                Benefity
              </Navbar.Brand>
              <Navbar.Brand href="/taxes" style={{ color: "gold" }}>
                Podatki
              </Navbar.Brand>
              <Navbar.Brand href="/stats" style={{ color: "gold" }}>
                Statystyki
              </Navbar.Brand>
            </>
          )}
        </Nav>

        <Nav>
          <Navbar.Brand
            onClick={logout}
            style={{ color: "gold", cursor: "pointer" }}
          >
            Wyloguj się
          </Navbar.Brand>
        </Nav>
      </Container>
    </Navbar>
  );
};

export default TopNavBar;
