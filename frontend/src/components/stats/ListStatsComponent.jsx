import React, { useState, useEffect } from "react";
import { getStatsForYear, getStatsForMonth } from "../../service/StatsService";
import { Form, Tabs, Tab, Card } from "react-bootstrap";

const ListStatsComponent = () => {
  const [tab, setTab] = useState("");
  const [selectedYear, setSelectedYear] = useState(
    new Date().getFullYear() - 1
  );
  const [selectedMonth, setSelectedMonth] = useState(new Date().getMonth() + 1);
  const [stats, setStats] = useState([]);

  const months = [
    "Styczeń",
    "Luty",
    "Marzec",
    "Kwiecień",
    "Maj",
    "Czerwiec",
    "Lipiec",
    "Sierpień",
    "Wrzesień",
    "Październik",
    "Listopad",
    "Grudzień",
  ];

  const getData = () => {
    if (tab === "year") {
      getStatsForYear(selectedYear.toString())
        .then((res) => setStats(res.data))
        .catch((err) => console.error(err));
    } else if (tab === "month") {
      getStatsForMonth(selectedYear.toString(), selectedMonth.toString())
        .then((res) => setStats(res.data))
        .catch((err) => console.error(err));
    }
  };

  useEffect(() => {
    getData();
  }, [selectedYear, selectedMonth, tab]);

  const renderYearOptions = () => {
    const lastYear = new Date().getFullYear() - 1;
    let yearOptions = [];
    for (let i = lastYear; i >= 2000; i--) {
      yearOptions.push(
        <option key={i} value={i}>
          {i}
        </option>
      );
    }
    return yearOptions;
  };

  const renderMonthOptions = () => {
    return months.map((month, index) => (
      <option key={index} value={index + 1}>
        {month}
      </option>
    ));
  };

  const renderStats = () => (
    <Card style={{ margin: "15px" }}>
      <Card.Body>
        {Array.isArray(stats) ? (
          stats.map((stat) => (
            <ul className="list-unstyled" key={stat.name}>
              <li>
                {stat.name} {": "}
                {stat.amount}zł
              </li>
            </ul>
          ))
        ) : (
          <p>Brak danych do wyświetlenia</p>
        )}
      </Card.Body>
    </Card>
  );

  return (
    <section>
      <Tabs
        activeKey={tab}
        onSelect={(t) => setTab(t)}
        style={{ margin: "15px" }}
        className="mb-3"
      >
        <Tab eventKey="year" title="Roczne">
          <Form style={{ margin: "15px" }}>
            <Form.Group controlId="formYearSelect">
              <Form.Label>Wybierz rok</Form.Label>
              <Form.Select
                value={selectedYear}
                onChange={(e) => setSelectedYear(e.target.value)}
              >
                {months.map((month, index) => (
                  <option key={index} value={index + 1}>
                    {month}
                  </option>
                ))}
              </Form.Select>
            </Form.Group>
          </Form>
          {renderStats()}
        </Tab>
        <Tab eventKey="month" title="Miesięczne">
          <Form style={{ margin: "15px" }}>
            <Form.Group controlId="formYearMonthSelect">
              <Form.Label>Wybierz rok</Form.Label>
              <Form.Select
                value={selectedYear}
                onChange={(e) => setSelectedYear(e.target.value)}
              >
                {renderYearOptions()}
              </Form.Select>
              <Form.Label>Wybierz miesiąc</Form.Label>
              <Form.Select
                value={selectedMonth}
                onChange={(e) => setSelectedMonth(e.target.value)}
              >
                {renderMonthOptions()}
              </Form.Select>
            </Form.Group>
          </Form>
          {renderStats()}
        </Tab>
      </Tabs>
    </section>
  );
};

export default ListStatsComponent;
