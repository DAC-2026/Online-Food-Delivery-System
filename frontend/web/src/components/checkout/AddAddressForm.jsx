import { useState } from "react";

function AddAddressForm({ onSave, onCancel }) {
  const [form, setForm] = useState({
    label: "",
    addressLine: "",
    city: "",
    pincode: "",
    latitude: "",
    longitude: "",
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = () => {
    onSave({
      ...form,
      latitude: Number(form.latitude) || 0,
      longitude: Number(form.longitude) || 0,
    });

    // reset form after successful save trigger
    setForm({
      label: "",
      addressLine: "",
      city: "",
      pincode: "",
      latitude: "",
      longitude: "",
    });
  };

  return (
    <div className="card p-3 mb-3">
      <h5>Add New Address</h5>

      <input
        name="label"
        className="form-control mb-2"
        placeholder="Label (Home / Office)"
        value={form.label}
        onChange={handleChange}
      />

      <input
        name="addressLine"
        className="form-control mb-2"
        placeholder="Address Line"
        value={form.addressLine}
        onChange={handleChange}
      />

      <input
        name="city"
        className="form-control mb-2"
        placeholder="City"
        value={form.city}
        onChange={handleChange}
      />

      <input
        name="pincode"
        className="form-control mb-2"
        placeholder="Pincode"
        value={form.pincode}
        onChange={handleChange}
      />

      <div className="d-flex gap-2">
        <button className="btn btn-success" onClick={handleSubmit}>
          Save Address
        </button>
        <button className="btn btn-secondary" onClick={onCancel}>
          Cancel
        </button>
      </div>
    </div>
  );
}

export default AddAddressForm;
