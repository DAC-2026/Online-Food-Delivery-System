import { useState } from "react";
import AddAddressForm from "./AddAddressForm";

function AddressSection({
  addresses,
  selectedAddressId,
  onSelect,
  onAddAddress,
}) {
  const [showAddForm, setShowAddForm] = useState(false);

  return (
    <>
      <h4 className="mb-3">Select Delivery Address</h4>

      {addresses.length === 0 && (
        <p>No address found. Please add one.</p>
      )}

      {addresses.map((address) => (
        <div
          key={address.addressId}
          className={`card mb-3 ${
            selectedAddressId === address.addressId
              ? "border-primary"
              : ""
          }`}
          style={{ cursor: "pointer" }}
          onClick={() => onSelect(address.addressId)}
        >
          <div className="card-body">
            <h6>{address.label}</h6>
            <p className="mb-0">
              {address.addressLine}, {address.city} –{" "}
              {address.pincode}
            </p>
          </div>
        </div>
      ))}

      <button
        className="btn btn-outline-secondary mb-3"
        onClick={() => setShowAddForm(!showAddForm)}
      >
        {showAddForm ? "Hide Address Form" : "+ Add New Address"}
      </button>

      {showAddForm && (
        <AddAddressForm
          onSave={(data) => {
            onAddAddress(data);
            setShowAddForm(false); // collapse after save
          }}
          onCancel={() => setShowAddForm(false)}
        />
      )}
    </>
  );
}

export default AddressSection;
