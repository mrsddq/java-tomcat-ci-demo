<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>My App Demo</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 0;
      min-height: 100vh;
      display: grid;
      place-items: center;
      background: #f5f7fb;
      color: #1f2937;
    }

    main {
      width: min(640px, calc(100% - 32px));
      padding: 32px;
      background: #ffffff;
      border: 1px solid #d9e0ea;
      border-radius: 8px;
      box-shadow: 0 16px 40px rgba(31, 41, 55, 0.08);
    }

    h1 {
      margin-top: 0;
      font-size: 2rem;
    }

    code {
      background: #eef2f7;
      padding: 2px 6px;
      border-radius: 4px;
    }
  </style>
</head>
<body>
  <main>
    <h1>My App Demo</h1>
    <p>This Java web application is packaged as <code>my-app-demo.war</code> and ready for Tomcat deployment.</p>
    <p>Build it with <code>mvn clean package</code>.</p>
    <p>Health check: <code>/health.jsp</code></p>
  </main>
</body>
</html>
