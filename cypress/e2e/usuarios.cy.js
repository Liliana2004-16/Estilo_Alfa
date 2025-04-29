describe('Pruebas de la API de Usuario', () => {
    const baseUrl = 'http://localhost:8080';
  
    it('debería listar todos los usuarios', () => {
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
  
    it('debería obtener un usuario por ID', () => {
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
  
    describe('Pruebas de Registro de Usuario', () => {
  
      it('debería registrar un nuevo usuario correctamente', () => {
        const randomNum = Math.floor(Math.random() * 100000);
        const randomEmail = `testuser${randomNum}@gmail.com`;
        const newUser = {
          nombre: 'Usuario de Prueba',
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
  
      it('debería fallar cuando el correo sea inválido', () => {
        const invalidUser = {
          nombre: 'Usuario de Prueba',
          correo: 'correo.invalido',
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
  
      it('debería fallar cuando el nombre esté vacío', () => {
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
  
      it('debería fallar cuando la contraseña esté vacía', () => {
        const invalidUser = {
          nombre: 'Usuario de Prueba',
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
  
      it('debería fallar cuando el rol sea inválido', () => {
        const invalidUser = {
          nombre: 'Usuario de Prueba',
          correo: 'testuser@gmail.com',
          contraseña: 'password123',
          rol: 'RolInvalido'
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
  
      it('debería fallar cuando el correo ya exista', () => {
        const existingUser = {
          nombre: 'Usuario de Prueba',
          correo: 'testuser@gmail.com',
          contraseña: 'password123',
          rol: 'Cliente'
        };
  
        cy.request({
          method: 'POST',
          url: `${baseUrl}/api/registro`,
          body: existingUser,
          failOnStatusCode: false
        });
  
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
