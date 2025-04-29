describe('Usuario API Tests', () => {
  const baseUrl = 'http://localhost:8080';

  it('should successfully register a new user', () => {
    const validUser = {
      nombre: 'Test User',
      correo: 'testuser@gmail.com',
      contraseña: 'password123',
      rol: 'Cliente'
    };

    cy.request({
      method: 'POST',
      url: `${baseUrl}/api/registro`,
      body: validUser,
      failOnStatusCode: false
    }).then((response) => {
      expect(response.status).to.eq(201);
      expect(response.body).to.have.property('nombre', validUser.nombre);
      expect(response.body).to.have.property('correo', validUser.correo);
      expect(response.body).to.have.property('rol', validUser.rol);
    });
  });

  it('should return error for empty name', () => {
    const invalidUser = {
      nombre: '',
      correo: 'test@gmail.com',
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
      expect(response.body).to.include('Nombre vacío');
    });
  });

  it('should return error for invalid email format', () => {
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
      expect(response.body).to.include('Correo inválido');
    });
  });

  it('should return error for empty password', () => {
    const invalidUser = {
      nombre: 'Test User',
      correo: 'test@gmail.com',
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
      expect(response.body).to.include('Contraseña vacía');
    });
  });

  it('should return error for invalid role', () => {
    const invalidUser = {
      nombre: 'Test User',
      correo: 'test@gmail.com',
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
      expect(response.body).to.include('Rol inválido');
    });
  });

  it('should get all users', () => {
    cy.request({
      method: 'GET',
      url: `${baseUrl}/api/usuarios`,
      failOnStatusCode: false
    }).then((response) => {
      expect(response.status).to.eq(200);
      expect(response.body).to.be.an('array');
    });
  });

  it('should get user by ID', () => {
    // First create a user to get its ID
    const newUser = {
      nombre: 'Test User ID',
      correo: 'testid@gmail.com',
      contraseña: 'password123',
      rol: 'Cliente'
    };

    cy.request({
      method: 'POST',
      url: `${baseUrl}/api/registro`,
      body: newUser,
      failOnStatusCode: false
    }).then((createResponse) => {
      expect(createResponse.status).to.eq(201);
      const userId = createResponse.body.id;

      // Now get the user by ID
      cy.request({
        method: 'GET',
        url: `${baseUrl}/api/usuario/${userId}`,
        failOnStatusCode: false
      }).then((getResponse) => {
        expect(getResponse.status).to.eq(200);
        expect(getResponse.body).to.have.property('id', userId);
        expect(getResponse.body).to.have.property('nombre', newUser.nombre);
      });
    });
  });

  it('should update user information', () => {
    // First create a user to update
    const newUser = {
      nombre: 'User to Update',
      correo: 'update@gmail.com',
      contraseña: 'password123',
      rol: 'Cliente'
    };

    cy.request({
      method: 'POST',
      url: `${baseUrl}/api/registro`,
      body: newUser,
      failOnStatusCode: false
    }).then((createResponse) => {
      expect(createResponse.status).to.eq(201);
      const userId = createResponse.body.id;

      // Now update the user
      const updatedUser = {
        id: userId,
        nombre: 'Updated User',
        correo: 'updated@gmail.com',
        contraseña: 'newpassword123',
        rol: 'Administrador'
      };

      cy.request({
        method: 'PUT',
        url: `${baseUrl}/api/usuario/actualizar`,
        body: updatedUser,
        failOnStatusCode: false
      }).then((updateResponse) => {
        expect(updateResponse.status).to.eq(200);
        expect(updateResponse.body).to.have.property('nombre', updatedUser.nombre);
        expect(updateResponse.body).to.have.property('correo', updatedUser.correo);
        expect(updateResponse.body).to.have.property('rol', updatedUser.rol);
      });
    });
  });

  it('should delete a user', () => {
    // First create a user to delete
    const newUser = {
      nombre: 'User to Delete',
      correo: 'delete@gmail.com',
      contraseña: 'password123',
      rol: 'Cliente'
    };

    cy.request({
      method: 'POST',
      url: `${baseUrl}/api/registro`,
      body: newUser,
      failOnStatusCode: false
    }).then((createResponse) => {
      expect(createResponse.status).to.eq(201);
      const userId = createResponse.body.id;

      // Now delete the user
      cy.request({
        method: 'DELETE',
        url: `${baseUrl}/api/eliminar/${userId}`,
        failOnStatusCode: false
      }).then((deleteResponse) => {
        expect(deleteResponse.status).to.eq(204);

        // Verify the user was deleted
        cy.request({
          method: 'GET',
          url: `${baseUrl}/api/usuario/${userId}`,
          failOnStatusCode: false
        }).then((getResponse) => {
          expect(getResponse.status).to.eq(404);
        });
      });
    });
  });

  it('should login successfully', () => {
    const loginCredentials = {
      correo: 'testuser@gmail.com',
      contraseña: 'password123'
    };

    cy.request({
      method: 'POST',
      url: `${baseUrl}/api/login`,
      body: loginCredentials,
      failOnStatusCode: false
    }).then((response) => {
      expect(response.status).to.eq(200);
      expect(response.body).to.have.property('correo', loginCredentials.correo);
    });
  });

  it('should fail login with wrong credentials', () => {
    const wrongCredentials = {
      correo: 'testuser@gmail.com',
      contraseña: 'wrongpassword'
    };

    cy.request({
      method: 'POST',
      url: `${baseUrl}/api/login`,
      body: wrongCredentials,
      failOnStatusCode: false
    }).then((response) => {
      expect(response.status).to.eq(401);
      expect(response.body).to.include('Correo o contraseña incorrectos');
    });
  });
}); 