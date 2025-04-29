describe('Citas API Tests', () => {
  const baseUrl = 'http://localhost:8080';

  it('should list all appointments', () => {
    cy.request({
      method: 'GET',
      url: `${baseUrl}/api/citas`,
      failOnStatusCode: false
    }).then((response) => {
      expect(response.status).to.eq(200);
      expect(response.body).to.be.an('array');
    });
  });

  it('should create a new appointment', () => {
    const newAppointment = {
      cliente: 'Juan Pérez',
      estado: 'programada',
      fechaHora: '2024-05-15T14:00:00.000Z',
      servicio_id: 1,
      usuario_id: 1,
      descripcion: 'Corte de cabello clásico'
    };

    cy.request({
      method: 'POST',
      url: `${baseUrl}/api/citas`,
      body: newAppointment,
      failOnStatusCode: false
    }).then((response) => {
      expect(response.status).to.eq(200);
      expect(response.body).to.have.property('cliente', newAppointment.cliente);
      expect(response.body).to.have.property('estado', newAppointment.estado);
      expect(response.body).to.have.property('descripcion', newAppointment.descripcion);
    });
  });

  it('should get an appointment by ID', () => {
    // First create an appointment to get its ID
    const newAppointment = {
      cliente: 'María López',
      estado: 'confirmada',
      fechaHora: '2024-05-16T15:00:00.000Z',
      servicio_id: 2,
      usuario_id: 2,
      descripcion: 'Corte + Barba'
    };

    cy.request({
      method: 'POST',
      url: `${baseUrl}/api/citas`,
      body: newAppointment,
      failOnStatusCode: false
    }).then((createResponse) => {
      expect(createResponse.status).to.eq(200);
      const appointmentId = createResponse.body.id;

      // Now get the appointment by ID
      cy.request({
        method: 'GET',
        url: `${baseUrl}/api/citas/${appointmentId}`,
        failOnStatusCode: false
      }).then((getResponse) => {
        expect(getResponse.status).to.eq(200);
        expect(getResponse.body).to.have.property('id', appointmentId);
        expect(getResponse.body).to.have.property('cliente', newAppointment.cliente);
      });
    });
  });

  it('should delete an appointment', () => {
    // First create an appointment to delete
    const newAppointment = {
      cliente: 'Pedro Martínez',
      estado: 'programada',
      fechaHora: '2024-05-17T16:00:00.000Z',
      servicio_id: 3,
      usuario_id: 1,
      descripcion: 'Corte Estilo Moderno'
    };

    cy.request({
      method: 'POST',
      url: `${baseUrl}/api/citas`,
      body: newAppointment,
      failOnStatusCode: false
    }).then((createResponse) => {
      expect(createResponse.status).to.eq(200);
      const appointmentId = createResponse.body.id;

      // Now delete the appointment
      cy.request({
        method: 'DELETE',
        url: `${baseUrl}/api/citas/${appointmentId}`,
        failOnStatusCode: false
      }).then((deleteResponse) => {
        expect(deleteResponse.status).to.eq(200);

        // Verify the appointment was deleted
        cy.request({
          method: 'GET',
          url: `${baseUrl}/api/citas/${appointmentId}`,
          failOnStatusCode: false
        }).then((getResponse) => {
          expect(getResponse.status).to.eq(404);
        });
      });
    });
  });
}); 