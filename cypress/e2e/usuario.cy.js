describe('Usuario API Tests', () => {
  const baseUrl = 'http://localhost:8080';

  it('should list all users', () => {
    cy.request({
      method: 'GET',
      url: `${baseUrl}/api/usuarios`,
      failOnStatusCode: false
    }).then((response) => {
      expect(response.status).to.eq(200);
      expect(response.body).to.be.an('array');
      expect(response.body.length).to.be.greaterThan(0);
    });
  });

  it('should get user by ID', () => {
    const existingUserId = 1; // ID del usuario "Carlos Sánchez"

    cy.request({
      method: 'GET',
      url: `${baseUrl}/api/usuario/${existingUserId}`,
      failOnStatusCode: false
    }).then((response) => {
      expect(response.status).to.eq(200);
      expect(response.body).to.have.property('id', existingUserId);
    });
  });

  describe('User Registration Tests', () => {
    it('should successfully register a new user', () => {
      const randomNum = Math.floor(Math.random() * 100000);
      const randomEmail = `testuser${randomNum}@gmail.com`;
      const newUser = {
        nombre: 'Test User',
        correo: randomEmail,
        contraseña: 'password123',
        rol: 'Cliente'
      };

      cy.request({
        method: 'POST',
        url: `${baseUrl}/api/registro`,
        body: newUser,
        failOnStatusCode: false
      }).then((response) => {
        expect(response.status).to.eq(201);
        expect(response.body).to.have.property('nombre', newUser.nombre);
        expect(response.body).to.have.property('correo', newUser.correo);
        expect(response.body).to.have.property('rol', newUser.rol);
      });
    });

    it('should fail when email is invalid', () => {
      const invalidUser = {
        nombre: 'Test User',
        correo: 'invalidemail',
        contraseña: 'password123',
        rol: 'Cliente'
      };

      cy.request({
        method: 'POST',
        url: `${baseUrl}/api/registro`,
        body: invalidUser,
        failOnStatusCode: false
      }).then((response) => {
        expect(response.status).to.eq(400);
        expect(response.body).to.eq('Correo inválido');
      });
    });

    it('should fail when name is empty', () => {
      const invalidUser = {
        nombre: '',
        correo: 'testuser@gmail.com',
        contraseña: 'password123',
        rol: 'Cliente'
      };

      cy.request({
        method: 'POST',
        url: `${baseUrl}/api/registro`,
        body: invalidUser,
        failOnStatusCode: false
      }).then((response) => {
        expect(response.status).to.eq(400);
        expect(response.body).to.eq('Nombre vacío');
      });
    });

    it('should fail when password is empty', () => {
      const invalidUser = {
        nombre: 'Test User',
        correo: 'testuser@gmail.com',
        contraseña: '',
        rol: 'Cliente'
      };

      cy.request({
        method: 'POST',
        url: `${baseUrl}/api/registro`,
        body: invalidUser,
        failOnStatusCode: false
      }).then((response) => {
        expect(response.status).to.eq(400);
        expect(response.body).to.eq('Contraseña vacía');
      });
    });

    it('should fail when role is invalid', () => {
      const invalidUser = {
        nombre: 'Test User',
        correo: 'testuser@gmail.com',
        contraseña: 'password123',
        rol: 'InvalidRole'
      };

      cy.request({
        method: 'POST',
        url: `${baseUrl}/api/registro`,
        body: invalidUser,
        failOnStatusCode: false
      }).then((response) => {
        expect(response.status).to.eq(400);
        expect(response.body).to.eq('Rol inválido');
      });
    });

    it('should fail when email already exists', () => {
      const existingUser = {
        nombre: 'Test User',
        correo: 'testuser@gmail.com',
        contraseña: 'password123',
        rol: 'Cliente'
      };

      // First registration
      cy.request({
        method: 'POST',
        url: `${baseUrl}/api/registro`,
        body: existingUser,
        failOnStatusCode: false
      });

      // Try to register with same email
      cy.request({
        method: 'POST',
        url: `${baseUrl}/api/registro`,
        body: existingUser,
        failOnStatusCode: false
      }).then((response) => {
        expect(response.status).to.eq(500);
        expect(response.body).to.eq('El correo ya existe');
      });
    });
  });
}); 