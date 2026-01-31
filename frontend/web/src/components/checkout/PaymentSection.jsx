function PaymentSection({ paymentMode, setPaymentMode }) {
  const modes = ["COD", "UPI", "CARD", "NET_BANKING"];

  return (
    <>
      <h4 className="mb-3">Select Payment Method</h4>

      {modes.map((mode) => (
        <div
          key={mode}
          className={`form-check p-2 mb-2 rounded ${
            paymentMode === mode ? "bg-light border" : ""
          }`}
          onClick={() => setPaymentMode(mode)}
          style={{ cursor: "pointer" }}
        >
          <input
            className="form-check-input"
            type="radio"
            checked={paymentMode === mode}
            readOnly
          />
          <label className="form-check-label ms-2">
            {mode}
          </label>
        </div>
      ))}
    </>
  );
}

export default PaymentSection;
